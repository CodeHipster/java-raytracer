package oostd.am.raytracer;

import oostd.am.raytracer.api.PixelSubscriberFactory;
import oostd.am.raytracer.api.camera.Camera;
import oostd.am.raytracer.api.camera.Color;
import oostd.am.raytracer.api.camera.Pixel;
import oostd.am.raytracer.api.camera.PixelSubscriber;
import oostd.am.raytracer.api.camera.Resolution;
import oostd.am.raytracer.api.debug.Line;
import oostd.am.raytracer.api.geography.PixelPosition;
import oostd.am.raytracer.api.geography.UnitVector;
import oostd.am.raytracer.api.geography.Vector;
import oostd.am.raytracer.api.geography.Vector2D;
import oostd.am.raytracer.api.scenery.ColorFilter;
import oostd.am.raytracer.api.scenery.PointLight;
import oostd.am.raytracer.api.scenery.Scene;
import oostd.am.raytracer.api.scenery.Triangle;
import oostd.am.raytracer.api.scenery.VolumeProperties;
import oostd.am.raytracer.collision.CollisionService;
import oostd.am.raytracer.debug.DebugLineProcessor;

import java.util.ArrayDeque;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.SubmissionPublisher;

/**
 * The engine renders a given scene from a camera perspective.
 * It does this asynchronously and continously generates pixel updates.
 * The pixels that are published will have to be added to previous pixels for the same pixelPosition.
 * This way each iteration provides more detail.
 * <p>
 * The engine first renders the first ray for each pixel of the render camera.
 * Then it goes full depth for each ray.
 */
public class Engine implements Runnable {

    private CollisionService collisionService;
    private List<PointLight> pointLights;
    //TODO: use it like a queue.
    private Queue<InverseRay> inverseRays = new ArrayDeque<>();
    private Queue<InverseRay> inverseRaysBuffer = new ArrayDeque<>();
    private Queue<ShadowRay> shadowRays = new ArrayDeque<>();
    private List<Triangle> triangles;
    private SubmissionPublisher<Pixel> renderOutput;
    private SubmissionPublisher<Line> debugLineOutput;

    ReflectionFactorCalculator reflectionFactorCalculator = new ReflectionFactorCalculator();

    public Engine(Scene scene, PixelSubscriberFactory pixelSubscriberFactory) {
        //TODO: decouple
        this.collisionService = new CollisionService(scene.triangles);
        this.renderOutput = new SubmissionPublisher<>();
        this.debugLineOutput = new SubmissionPublisher<>();
        PixelSubscriber renderSubscriber = pixelSubscriberFactory.createRenderSubscriber(scene.renderCamera.lens.name);
        renderOutput.subscribe(renderSubscriber);

        scene.debugWindows.forEach(window -> {
            // consumes Pixels
            PixelSubscriber pixelSubscriber = pixelSubscriberFactory.createDebugSubscriber(window.name);
            // consumes Lines, produces Pixels
            DebugLineProcessor lineToPixelProcessor = new DebugLineProcessor(window, pixelSubscriber.getResolution());
            // hook up pixel consumer to pixel producer
            lineToPixelProcessor.subscribe(pixelSubscriber);
            // hook up line consumer to line producer
            debugLineOutput.subscribe(lineToPixelProcessor);
        });

        this.pointLights = scene.pointLights;
        this.triangles = scene.triangles;

        initializeRays(scene.renderCamera, renderSubscriber.getResolution());
    }

    /**
     * Initailize the first rays from the scene.
     * The rays start from the camera position and are aimed at a 1 by 1 unit square at given offset.
     *
     * @param camera
     */
    private void initializeRays(Camera camera, Resolution resolution) {
        Vector point = camera.lens.positionOf(new Vector2D(1, 1));
        double xStep = camera.lens.dimension.width / resolution.width;
        double yStep = camera.lens.dimension.height / resolution.height;
        //start in the left bottom
        double xStart = (camera.lens.dimension.width / -2) + (xStep / 2); //+half a step to move to the center of the pixel
        double yStart = camera.lens.dimension.height / -2 + (yStep / 2);

        for (int y = 0; y < resolution.height; ++y) {
            double yLensPos = yStart + y * yStep;
            for (int x = 0; x < resolution.width; ++x) {
                double xLensPos = xStart + x * xStep;
                Vector lensPoint = camera.lens.positionOf(new Vector2D(xLensPos, yLensPos));
                Vector camToPoint = lensPoint.subtract(camera.position);
                UnitVector rayDirection = new UnitVector(camToPoint);
                InverseRay ray = new InverseRay(
                        1,
                        1,
                        rayDirection,
                        camera.position,
                        new PixelPosition(x, y),
                        null,
                        //expect the camera to be in the void.
                        // TODO: check if the camera is inside an object and use those properties.
                        new VolumeProperties(new ColorFilter(1, 1, 1), 1));
                inverseRays.add(ray);
            }
        }
    }

    /**
     * output lines for geometry
     */
    public void debugSceneGeometry() {
        triangles.forEach(triangle -> {
            debugLineOutput.submit(new Line(triangle.vertices[0], triangle.vertices[1], Color.WHITE));
            debugLineOutput.submit(new Line(triangle.vertices[1], triangle.vertices[2], Color.WHITE));
            debugLineOutput.submit(new Line(triangle.vertices[2], triangle.vertices[0], Color.WHITE));
        });
        // print axis
        debugLineOutput.submit(new Line(
                new Vector(0, 0, 0),
                new Vector(1, 0, 0),
                Color.RED));
        debugLineOutput.submit(new Line(
                new Vector(0, 0, 0),
                new Vector(0, 1, 0),
                Color.GREEN));
        debugLineOutput.submit(new Line(
                new Vector(0, 0, 0),
                new Vector(0, 0, 1),
                Color.BLUE));
    }

    @Override
    public void run() {
        debugSceneGeometry();
        boolean running = true;
        while (running) {
            processInverseRays();

            processShadowRays();

            // If there are no new rays generated, we can stop the engine.
            if (inverseRays.isEmpty()) {
                running = false;
            }
        }
        System.out.println("finished tracing rays");
        renderOutput.close();
        debugLineOutput.close();
    }

    private void swapBuffer() {
        Queue<InverseRay> temp = inverseRays;
        inverseRays = inverseRaysBuffer;
        inverseRaysBuffer = temp;
    }

    //https://www.scratchapixel.com/lessons/3d-basic-rendering/introduction-to-shading/reflection-refraction-fresnel
    //Returns null when there is no refraction, because of total internal reflection.
    private UnitVector calculateRefractionDirection(double refractionIndexFrom, double refractionIndexTo, InverseRay ray, Triangle target, boolean hitFromBehind) {

        UnitVector I = ray.direction;
        UnitVector N = (hitFromBehind) ? target.surfaceNormal.invert() : target.surfaceNormal;
        double n = refractionIndexFrom / refractionIndexTo;

        double c1 = I.dot(N);

        double tir = 1 - (n * n) * (1 - (c1 * c1));
        if (tir < 0) {
            return null; //Total internal reflection
        }
        double c2 = Math.sqrt(tir);

        Vector T = I.scale(n).add(N.scale(n * c1 - c2));

        return new UnitVector(T);
    }

    private void processInverseRays() {

        inverseRays.stream().forEach(
                ray -> {
                    Collision collision = collisionService.findCollision(ray, ray.origin);
                    if (collision == null) return; // We did not hit anything. No light comes from the void.

                    Triangle target = collision.target;

                    debugLineOutput.submit(new Line(ray.position, collision.impactPoint, target.material.colorFilter.filter(Color.WHITE)));

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
        if(direction == null){
            int de = 1;
        }
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
                    collision.target,
                    origin.volumeProperties);
            inverseRaysBuffer.add(reflection);
        } else {
            System.out.println("Not casting ray. Intensity: " + intensity + " depth: " + depth);
        }
    }

    private void castShadowRay(double diffuseFactor, InverseRay ray, Collision collision) {
        double lightIntensity = diffuseFactor * ray.intensity;

        //TODO: add color filter based on refraction factor. (for reflection and diffuse)
        //for each light create a lightray
        if (lightIntensity > 0.001) { //no need to add the light when it has to little impact on the scene.
            for (PointLight light : pointLights) {
                shadowRays.add(new ShadowRay(
                        light,
                        collision.target,
                        collision.impactPoint,
                        ray,
                        ray.volumeProperties));
            }
        } else {
            System.out.println("Not casting shadow ray. Intensity: " + lightIntensity + " depth: " + ray.depth);
        }
    }

    private void castReflectionRay(InverseRay ray, double reflectionFactor, Collision collision, boolean hitFromBehind) {

        //create reflection ray.
        int depth = ray.depth + 1;
        System.out.println("Creating reflection ray. depth: " + depth);
        double intensity = ray.intensity * reflectionFactor;
        if (intensity > 0.001 && depth < 20) { //Only create ray if it has an impact on the scene.
            UnitVector surfaceNormal = (hitFromBehind) ? collision.target.surfaceNormal.invert() : collision.target.surfaceNormal;
            UnitVector direction = ray.direction.reflectOn(surfaceNormal);
            //volumeProperties is the same, as we stay within the same space.
            InverseRay reflection = new InverseRay(
                    depth,
                    intensity,
                    direction,
                    collision.impactPoint,
                    ray.pixelPosition,
                    collision.target,
                    ray.volumeProperties);
            inverseRaysBuffer.add(reflection);
        } else {
            System.out.println("Not casting reflection ray. Intensity: " + intensity + " depth: " + depth);
        }
    }

    private void castRefractionRay(InverseRay ray, double refractionFactor, Collision collision, boolean hitFromBehind) {
        //cast refraction ray.
        int depth = ray.depth + 1;
        System.out.println("Creating refraction ray. depth: " + depth);
        double intensity = ray.intensity * refractionFactor;
        if (intensity > 0.001 && depth < 20) { //no need to add the light as it has to little impact on the scene.
            UnitVector vector;
            if (hitFromBehind) {
                vector = calculateRefractionDirection(collision.target.volumeProperties.refractionIndex,
                        1, ray, collision.target, hitFromBehind);
            } else {
                vector = calculateRefractionDirection(1,
                        collision.target.volumeProperties.refractionIndex, ray, collision.target, hitFromBehind);
            }
            if (vector == null) { // Total internal reflection
                //cast reflection ray
                castReflectionRay(ray, refractionFactor, collision, hitFromBehind);
                System.out.println("Not casting refraction ray. Intensity: " + intensity + " depth: " + depth);
            } else {
                InverseRay inverseRay = new InverseRay(depth, intensity, new UnitVector(vector), collision.impactPoint, ray.pixelPosition, collision.target, collision.target.volumeProperties);
                inverseRaysBuffer.add(inverseRay);
            }
        } else {
            System.out.println("Not casting refraction ray. Intensity: " + intensity + " depth: " + depth);
        }
    }

    // Part of the light that is reflected, calculated by base reflection + factor based on angle.
    private double getReflectionFactor(Triangle target, Ray ray, boolean hitFromBehind) {
        // initial refractionFactor = (1 - reflectionFactor) * refractionFactor. e.g. (1-0.1) * 0.5 = 0.45
        double reflectionFactor = target.material.reflectionFactor;
        double additionalReflectionFactor;
        if (hitFromBehind) {
            //TODO figure out a way to know the refraction index of the volume we are going into.
            //maintain a stack of densities? or not allow objects to intersect?
            additionalReflectionFactor = reflectionFactorCalculator.calculateReflectionFactor(
                    target.surfaceNormal.invert(), ray.direction.invert(), target.volumeProperties.refractionIndex, 1);
        } else {
            additionalReflectionFactor = reflectionFactorCalculator.calculateReflectionFactor(
                    target.surfaceNormal, ray.direction.invert(), 1, target.volumeProperties.refractionIndex);
        }
        double total = (1 - reflectionFactor) * additionalReflectionFactor + reflectionFactor;
        if (total > 1) {
            int debug = 0;
        }
        return total;
    }

    private void processShadowRays() {
        shadowRays.stream().forEach(shadowRay -> {
            //if light shines on back of triangle it will not hit the visible side.
            double frontFacing = shadowRay.triangle.surfaceNormal.dot(shadowRay.direction);
            if (frontFacing <= 0) {
                return;
            }

            if (!rayHitsLight(shadowRay)) {
                return;
            }

            debugLineOutput.submit(new Line(shadowRay.position, shadowRay.light.position, shadowRay.light.color));

            Color color = PhongReflection.calculatePhong(shadowRay.inverseRay, shadowRay.light, shadowRay.position, shadowRay.triangle);
            renderOutput.submit(new Pixel(shadowRay.inverseRay.pixelPosition, color));
        });
        shadowRays.clear();
    }

    //Check if shadowRay hits the light.
    private boolean rayHitsLight(ShadowRay shadowRay) {

        double distanceToLight = shadowRay.light.position.subtract(shadowRay.position).length();
        for (Triangle triangle : triangles) {
            if (triangle == shadowRay.triangle) {
                //No need to check collision with self.
                continue;
            }
            double collisionDistance = CollisionCalculator.calculateCollisionDistance(triangle, shadowRay);
            if (collisionDistance < 0) continue; //triangle was not hit.
            if (collisionDistance < distanceToLight) {
                return false;
            }
        }
        return true; // No triangles in the way.
    }
}