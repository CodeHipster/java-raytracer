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
                //refraction factor of 1, as we assume to start within a vacuum
                inverseRays.add(new InverseRay(1, 1, 1, rayDirection, camera.positioning.position, new PixelPosition(x, y),null));
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

                        //Add refraction
                        // initial refractionFactor = (1 - reflectionFactor) * refractionFactor. e.g. (1-0.1) * 0.5 = 0.45
                        //refractionFactor = reflection.normal^2 (at 90 degrees it is 1)
                        ReflectionFactorCalculator calculator = new ReflectionFactorCalculator();
                        double reflectionFactor = target.material.reflectionFactor;
                        double additionalReflectionFactor = calculator.calculateReflectionFactor(target.surfaceNormal, ray.direction.inverse(), target.material.refractionIndex, ray.refractionIndex);
                        double nonReflectionFactor = 1 - additionalReflectionFactor;
                        nonReflectionFactor = (1 - reflectionFactor) * nonReflectionFactor;
                        additionalReflectionFactor = (1 - reflectionFactor) * additionalReflectionFactor;
                        double totalReflectionFactor = reflectionFactor + additionalReflectionFactor;


                        if(totalReflectionFactor > 0){
                            //create reflection ray.
                            int depth = ray.depth + 1;
                            System.out.println("Creating reflection ray. depth: " + depth);
                            double intensity = ray.intensity * totalReflectionFactor;
                            if(intensity > 0.001){ //no need to add the light as it has to little impact on the scene.
                                UnitVector direction = ray.direction.reflectOn(target.surfaceNormal);
                                //refraction factor is the same, as we stay within the same space.
                                InverseRay reflection = new InverseRay(depth, intensity, ray.refractionIndex, direction, collision,ray.destination, target);
                                inverseRaysBuffer.add(reflection);
                            }
                        }

                        if(target.material.transparent){ //cast refraction ray.
                            int depth = ray.depth + 1;
                            System.out.println("Creating refraction ray. depth: " + depth);
                            double intensity = ray.intensity * nonReflectionFactor;
                            if(intensity > 0.001){ //no need to add the light as it has to little impact on the scene.
                                UnitVector vector = calculateRefractionDirection(ray.refractionIndex, target.material.refractionIndex, ray, target);
                                int debug = 0;

                            }
                        }else{
                            double lightIntensity = nonReflectionFactor * ray.intensity;

                            //TODO: add color filter based on refraction factor. (for reflection and diffuse)
                            //for each light create a lightray
                            if(lightIntensity > 0.001) { //no need to add the light as it has to little impact on the scene.
                                for (PointLight light : pointLights) {
                                    shadowRays.add(new ShadowRay(light, target, collision, ray.destination, ray, lightIntensity));
                                }
                            }else{
                                System.out.println("Intensity of lightray at: " + lightIntensity + " depth: " + ray.depth);
                            }
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
                Color diffuse = shadowRay.triangle.material.colorFilter.filter(shadowRay.light.color.clone()).scale(diffuseFactor);

                //TODO: actual intensity /  how much it adds to the color? That is based on the intensity of all predecessor rays.
                Color color = diffuse.add(specular).scale(shadowRay.intensity);
                //Color color = diffuse.scale(shadowRay.intensity);
                pixelSink.submit(new Pixel(shadowRay.destination, color));

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

    private UnitVector calculateRefractionDirection(double refractionIndexFrom, double refractionIndexTo, InverseRay ray, Triangle target){

        UnitVector I = ray.direction;
        UnitVector N = target.surfaceNormal;
        double n = refractionIndexFrom / refractionIndexTo;

        double c1 = I.dot(N);

        double c2 = Math.sqrt(1 - (n*n) * (1-(c1*c1)));

        Vector T = I.scale(n).add(N.scale(n*c1-c2));

        return UnitVector.construct(T);
    }
}