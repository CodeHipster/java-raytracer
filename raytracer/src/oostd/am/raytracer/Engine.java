package oostd.am.raytracer;

import oostd.am.raytracer.api.camera.Camera;
import oostd.am.raytracer.api.camera.Color;
import oostd.am.raytracer.api.camera.Pixel;
import oostd.am.raytracer.api.geography.PixelPosition;
import oostd.am.raytracer.api.geography.UnitVector;
import oostd.am.raytracer.api.geography.Vector;
import oostd.am.raytracer.api.scenery.PointLight;
import oostd.am.raytracer.api.scenery.Scene;
import oostd.am.raytracer.api.scenery.Triangle;

import java.util.ArrayDeque;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.SubmissionPublisher;

/**
 * The engine renders a given scene from a camera perspective.
 * It does this asynchronously and continously generates pixel updates.
 * The pixels that are published will have to be added to previous pixels for the same destination.
 * This way each iteration provides more detail.
 */
public class Engine implements Runnable{
    private List<PointLight> pointLights;
    private Queue<InverseRay> inverseRays = new ArrayDeque<>();
    private Queue<InverseRay> inverseRaysBuffer = new ArrayDeque<>();
    private Queue<ShadowRay> shadowRays = new ArrayDeque<>();
    private List<Triangle> triangles;
    private SubmissionPublisher<Pixel> pixelSink;

    public Engine(Camera camera, Scene scene, SubmissionPublisher<Pixel> pixelSink) {
        this.pixelSink = pixelSink;

        this.pointLights = scene.getPointLights();
        this.triangles = scene.getTriangles();

        int pixelsX = camera.lens.width;
        int pixelsY = camera.lens.height;
        //TODO: use camera direction as well.
        //TODO: lens position and size in units
        //for now using 1 by 1 unit.
        double width = 1;
        double height = 1;
        for (int y = 0; y < pixelsY; ++y) {
            double ypos = ((double) y / pixelsY) * height - height / 2.0;
            for (int x = 0; x < pixelsX; ++x) {
                double xpos = ((double) x / pixelsX) * width - width / 2.0;
                UnitVector rayDirection = UnitVector.construct(xpos, ypos, camera.lens.offset);
                inverseRays.add(new InverseRay(1, 1, rayDirection, camera.positioning.position, new PixelPosition(x, y),null));
            }
        }
    }

    @Override
    public void run() {
        boolean running = true;
        while(running) {
            inverseRays.stream().forEach(
                    ray ->{
                        //check collision with triangles;
                        double distance = Double.POSITIVE_INFINITY;
                        Triangle target = null;

                        for (Triangle triangle : triangles) {
                            if(triangle == ray.origin) continue; //skip triangle we come from.
                            double d = CollisionCalculator.calculateCollisionDistance(triangle, ray);
                            if (d > 0 && d < distance) {
                                distance = d;
                                target = triangle;
                            }
                        }
                        if(target == null) return;
                        Vector collision = ray.position.add(ray.direction.scale(distance));

                        if(target.material.reflectance > 0){
                            //todo if intensity goes below a certain level, stop reflecting.
                            //create reflection ray.
                            int depth = ray.depth + 1;
                            System.out.println("Creating reflection ray. depth: " + depth);
                            double intensity = ray.intensity * target.material.reflectance;
                            UnitVector direction = ray.direction.reflectOn(target.surfaceNormal);
                            InverseRay reflection = new InverseRay(depth, intensity, direction, collision,ray.destination, target);
                            inverseRaysBuffer.add(reflection);
                        }

                        //TODO: fresnel, to figure out the actual reflectance factor
                        double lightIntensity = (1 - target.material.reflectance) * ray.intensity;

                        //for each light create a lightray
                        for (PointLight light : pointLights) {
                            shadowRays.add(new ShadowRay(light, target, collision, ray.getDestination(), ray, lightIntensity));
                        }
                    }
            );
            inverseRays.clear();

            // If there are no new rays generated, we can stop the engine.
            if(inverseRaysBuffer.isEmpty()){
                running = false;
            }

            swapBuffer();

            shadowRays.stream().forEach(shadowRay ->{
                //check if ray reaches the light;
                //if light is behind triangle it will not hit.
                UnitVector lightNormal = shadowRay.direction.inverse();
                UnitVector inverseLightNormal = shadowRay.direction;
                UnitVector surfaceNormal = shadowRay.triangle.surfaceNormal;
                double diffuseFactor = surfaceNormal.dot(inverseLightNormal);
                if(diffuseFactor < 0) {
                    return;
                }

                double distanceToLightSquared = shadowRay.light.getPosition().subtract(shadowRay.position).square();
                for (Triangle triangle : triangles) {
                    if (triangle == shadowRay.triangle) {
                        //No need to check collision with self.
                        continue;
                    }
                    double collisionDistance = CollisionCalculator.calculateCollisionDistance(triangle, shadowRay);
                    if(collisionDistance < 0) continue; //triangle was not hit.
                    double collisionSquared = collisionDistance * collisionDistance;
                    if (collisionSquared < distanceToLightSquared) {
                        return;
                    }
                }

                //calculate color
                UnitVector reflectNormal = lightNormal.reflectOn(surfaceNormal);
                UnitVector inverseViewNormal = shadowRay.inverseRay.direction.inverse();

                double specularIntensity = shadowRay.triangle.material.specularIntensity;
                double specularPower = shadowRay.triangle.material.specularPower;
                double specularFactor = reflectNormal.dot(inverseViewNormal);
                if(specularFactor < 0){
                    //makes no sense to apply specular if the light does not reflect in the direction of the eye.
                    specularFactor = 0;
                }else{
                    specularFactor = Math.pow(specularFactor, specularPower);
                }
                Color specular = shadowRay.light.color.clone().scale(specularIntensity * specularFactor);

                if(specularIntensity == 0 && specular.r != 0){
                    int debug = 0;
                }
                Color diffuse = shadowRay.triangle.material.colorFilter.filter(shadowRay.light.color.clone()).scale(diffuseFactor);

                //TODO: actual intensity /  how much it adds to the color? That is based on the intensity of all predecessor rays.
                Color color = diffuse.add(specular).scale(shadowRay.intensity);
                //Color color = diffuse.scale(shadowRay.intensity);
                pixelSink.submit(new Pixel(shadowRay.getDestination(), color));

            });

            shadowRays.clear();

        }
        System.out.println("finished tracing rays");
        pixelSink.close();
    }

    private void swapBuffer(){
        Queue<InverseRay> temp = inverseRays;
        inverseRays = inverseRaysBuffer;
        inverseRaysBuffer = temp;
    }
}