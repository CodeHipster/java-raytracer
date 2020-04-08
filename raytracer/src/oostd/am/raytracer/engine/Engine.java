package oostd.am.raytracer.engine;

import oostd.am.raytracer.api.PixelSubscriberFactory;
import oostd.am.raytracer.api.camera.Color;
import oostd.am.raytracer.api.camera.Pixel;
import oostd.am.raytracer.api.camera.PixelSubscriber;
import oostd.am.raytracer.api.geography.UnitVector;
import oostd.am.raytracer.api.scenery.PointLight;
import oostd.am.raytracer.api.scenery.Scene;
import oostd.am.raytracer.api.scenery.Triangle;
import oostd.am.raytracer.engine.collision.RayCollider;
import oostd.am.raytracer.engine.debug.Debugger;

import java.util.ArrayDeque;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.SubmissionPublisher;

/**
 * The engine renders a given scene from a camera perspective.
 * It does this asynchronously and continuously generating pixel updates.
 * The pixels that are published will have to be added to previous pixels for the same pixelPosition.
 * This way each iteration provides more detail.
 * <p>
 * The engine first renders the first ray for each pixel of the render camera.
 * Then it goes full depth for each ray.
 */
public class Engine implements Runnable {

    // scene
    private List<PointLight> pointLights;
    private List<Triangle> triangles;

    //TODO: use it like a queue.
    // rays to process
    private Queue<InverseRay> inverseRays = new ArrayDeque<>();
    private Queue<InverseRay> inverseRaysBuffer = new ArrayDeque<>();
    private Queue<LightRay> lightRays = new ArrayDeque<>();

    // engine output
    private SubmissionPublisher<Pixel> renderOutput;

    private Debugger debugger;

    public Engine(Scene scene, PixelSubscriberFactory pixelSubscriberFactory) {
        this.pointLights = scene.pointLights;
        this.triangles = scene.triangles;

        //TODO: decouple
        this.debugger = new Debugger(scene.debugWindows, pixelSubscriberFactory);
        this.renderOutput = new SubmissionPublisher<>();
        PixelSubscriber renderSubscriber = pixelSubscriberFactory.createRenderSubscriber(scene.renderCamera.lens.name);
        renderOutput.subscribe(renderSubscriber);

        inverseRays.addAll(RayInitializer.createRays(scene.renderCamera, renderSubscriber.getResolution()));
    }

    @Override
    public void run() {
        debugger.drawSceneGeometry(triangles);
        boolean running = true;
        while (running) {
            processInverseRays();

            processLightRays();

            // If there are no new rays generated, we can stop the engine.
            if (inverseRays.isEmpty()) {
                running = false;
            }
        }
        System.out.println("finished tracing rays");
        renderOutput.close();
        debugger.close();
    }

    private void swapBuffer() {
        Queue<InverseRay> temp = inverseRays;
        inverseRays = inverseRaysBuffer;
        inverseRaysBuffer = temp;
    }

    //TODO: refactor
    private void processInverseRays() {

        inverseRays.forEach(
                ray -> {
                    Collision collision = RayCollider.findCollision(ray, ray.origin, triangles);
                    if (collision == null) return; // We did not hit anything. No light comes from the void.

                    Triangle target = collision.target;

                    debugger.line(ray, collision, target);

                    boolean hitFromBehind = (ray.direction.dot(target.surfaceNormal) > 0);
                    ReflectionService.Scatter scatter = new ReflectionService().scatter(ray, collision);
                    if (hitFromBehind) {
                        castRay(ray, collision, scatter.reflection, scatter.reflectionFactor);
                        if (scatter.refraction != null) {
                            castRay(ray, collision, scatter.refraction, scatter.refractionFactor);
                        }
                        //ray could split in 2 directions:
                        // internal reflection (inverse ray)
                        // refraction (inverse ray)
                    } else {
                        // calculate factors
                        //ray could split in 3 directions:
                        // reflection (inverse ray)
                        // refraction (inverse ray)
                        // light (shadow ray)
                        double materialDiffuse = target.material.diffuseFactor;
                        double materialReflection = target.material.reflectionFactor;
                        double materialRefraction = 1 - materialReflection - materialDiffuse;
                        double reflectionFactor = materialReflection + (materialRefraction * scatter.reflectionFactor);
                        double refractionFactor = materialRefraction * scatter.refractionFactor;
                        double diffuseFactor = materialDiffuse;

                        if (scatter.reflection != null) {
                            castRay(ray, collision, scatter.reflection, reflectionFactor);
                        }
                        if (scatter.refraction != null) {
                            castRay(ray, collision, scatter.refraction, refractionFactor);
                        }
                        castShadowRay(diffuseFactor, ray, collision);
                    }
                }
        );
        inverseRays.clear();

        // As processing inverserays create new inverse rays which we want to process at a later time we place them in a buffer.
        // And swap the buffer when the rays have been processed.
        swapBuffer();
    }

    private void castRay(InverseRay origin, Collision collision, UnitVector direction, double factor) {
        int depth = origin.depth + 1;
        double intensity = origin.intensity * factor;
        if (intensity > 0.001 && depth < 20) { //Only create ray if it has an impact on the scene.
            System.out.println("casting new ray. depth: " + depth);
            InverseRay reflection = new InverseRay(
                    depth,
                    intensity,
                    direction,
                    collision.impactPoint,
                    origin.pixelPosition,
                    collision.target);
            inverseRaysBuffer.add(reflection);
        } else {
            System.out.println("Not casting ray. Intensity: " + intensity + " depth: " + depth);
        }
    }

    private void castShadowRay(double diffuseFactor, InverseRay ray, Collision collision) {
        double lightIntensity = diffuseFactor * ray.intensity;

        //for each light create a lightray
        if (lightIntensity > 0.001) { //no need to add the light when it has to little impact on the scene.
            for (PointLight light : pointLights) {
                lightRays.add(new LightRay(
                        light,
                        collision.target,
                        collision.impactPoint,
                        ray,
                        lightIntensity));
            }
        } else {
            System.out.println("Not casting shadow ray. Intensity: " + lightIntensity + " depth: " + ray.depth);
        }
    }

    private void processLightRays() {
        lightRays.forEach(lightRay -> {
            //if light shines on back of triangle it will not hit the visible side.
            //TODO: should we add a property to a ray for being inside or outside an object?
            double frontFacing = lightRay.triangle.surfaceNormal.dot(lightRay.direction);
            if (frontFacing <= 0) {
                return;
            }

            if (!rayHitsLight(lightRay)) {
                return;
            }

            debugger.line(lightRay);

            Color color = PhongReflection.calculatePhong(lightRay.inverseRay, lightRay.light, lightRay.position, lightRay.triangle);
            Color scale = color.scale(lightRay.intensity);
            renderOutput.submit(new Pixel(lightRay.inverseRay.pixelPosition, scale));
        });
        lightRays.clear();
    }

    //Check if shadowRay hits the light.
    private boolean rayHitsLight(LightRay lightRay) {

        double distanceToLight = lightRay.light.position.subtract(lightRay.position).length();
        for (Triangle triangle : triangles) {
            if (triangle == lightRay.triangle) {
                //No need to check collision with self.
                continue;
            }
            double collisionDistance = CollisionCalculator.calculateCollisionDistance(triangle, lightRay);
            if (collisionDistance < 0) continue; //triangle was not hit.
            if (collisionDistance < distanceToLight) {
                return false;
            }
        }
        return true; // No triangles in the way.
    }
}