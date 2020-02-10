package oostd.am.raytracer;

import oostd.am.raytracer.api.camera.Camera;
import oostd.am.raytracer.api.camera.Color;
import oostd.am.raytracer.api.camera.Pixel;
import oostd.am.raytracer.api.geography.PixelPosition;
import oostd.am.raytracer.api.geography.UnitVector;
import oostd.am.raytracer.api.geography.Vector;
import oostd.am.raytracer.api.scenery.*;

import java.util.ArrayDeque;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.SubmissionPublisher;

/**
 * The engine renders a given scene from a camera perspective.
 * It does this asynchronously and continously generates pixel updates.
 * The pixels that are published will have to be added to previous pixels for the same pixelPosition.
 * This way each iteration provides more detail.
 */
public class Engine implements Runnable{
    private List<PointLight> pointLights;
    private Queue<InverseRay> inverseRays = new ArrayDeque<>();
    private Queue<InverseRay> inverseRaysBuffer = new ArrayDeque<>();
    private Queue<ShadowRay> shadowRays = new ArrayDeque<>();
    private List<Triangle> triangles;
    private SubmissionPublisher<Pixel> pixelSink;

    ReflectionFactorCalculator reflectionFactorCalculator = new ReflectionFactorCalculator();

    public Engine(Scene scene) {
        this.pixelSink = new SubmissionPublisher<>();
        Camera camera = scene.getRenderCamera();
        pixelSink.subscribe(camera.outputConsumer);

        this.pointLights = scene.getPointLights();
        this.triangles = scene.getTriangles();

        int pixelsX = camera.resolution.width;
        int pixelsY = camera.resolution.height;
        //TODO: use camera direction as well.
        //TODO: lens position and size in units
        //for now using 1 by 1 unit.
        double width = 1;
        double height = 1;
        for (int y = 0; y < pixelsY; ++y) {
            double ypos = ((double) y / pixelsY) * height - height / 2.0;
            for (int x = 0; x < pixelsX; ++x) {
                double xpos = ((double) x / pixelsX) * width - width / 2.0;
                UnitVector rayDirection = UnitVector.construct(xpos, ypos, camera.lensOffset);
                InverseRay ray = new InverseRay(
                        1
                        , 1
                        , rayDirection
                        , camera.positioning.position
                        , new PixelPosition(x, y)
                        , null
                        , new VolumeProperties(new ColorFilter(1,1,1), 1)); //expect the camera to be in the void. TODO: check if the camera is inside an object and use those properties.
                inverseRays.add(ray);
            }
        }
    }

    @Override
    public void run() {
        boolean running = true;
        while(running) {
            processInverseRays();

            processShadowRays();

            // If there are no new rays generated, we can stop the engine.
            if(inverseRays.isEmpty()){
                running = false;
            }
        }
        System.out.println("finished tracing rays");
        pixelSink.close();
    }

    private void swapBuffer(){
        Queue<InverseRay> temp = inverseRays;
        inverseRays = inverseRaysBuffer;
        inverseRaysBuffer = temp;
    }

    //https://www.scratchapixel.com/lessons/3d-basic-rendering/introduction-to-shading/reflection-refraction-fresnel
    //Returns null when there is no refraction, because of total internal reflection.
    private UnitVector calculateRefractionDirection(double refractionIndexFrom, double refractionIndexTo, InverseRay ray, Triangle target, boolean hitFromBehind){

        UnitVector I = ray.direction;
        UnitVector N = (hitFromBehind)?target.surfaceNormal.inverse():target.surfaceNormal;
        double n = refractionIndexFrom / refractionIndexTo;

        double c1 = I.dot(N);

        double tir = 1 - (n * n) * (1 - (c1 * c1));
        if(tir < 0) {
            return null; //Total internal reflection
        }
        double c2 = Math.sqrt(tir);

        Vector T = I.scale(n).add(N.scale(n*c1-c2));

        return UnitVector.construct(T);
    }

    private Collision findCollision(Ray ray, Triangle origin){

        //check collision with triangles;
        double distance = Double.POSITIVE_INFINITY;
        Triangle target = null;

        for (Triangle triangle : triangles) {
            if(triangle == origin) continue; //skip triangle we come from.
            double d = CollisionCalculator.calculateCollisionDistance(triangle, ray);
            if (d > 0 && d < distance) {
                distance = d;
                target = triangle;
            }
        }
        if(target == null) return null; // did not hit anything.
        Vector collisionPoint = ray.position.add(ray.direction.scale(distance));
        return new Collision(target, collisionPoint);
    }

    private void processInverseRays(){

        inverseRays.stream().forEach(
                ray ->{
                    Collision collision = findCollision(ray, ray.origin);
                    if(collision == null) return; // We did not hit anything. No light comes from the void.
                    Triangle target = collision.target;

                    boolean hitFromBehind = (ray.direction.dot(target.surfaceNormal) > 0);

                    double totalReflectionFactor = getReflectionFactor(target, ray, hitFromBehind);
                    double nonReflectionFactor = 1 - totalReflectionFactor; // part of light which is not reflected, either refracted or diffuse.

                    if(totalReflectionFactor > 0){
                        castReflectionRay(ray, totalReflectionFactor, collision, hitFromBehind);
                    }

                    if(target.material.transparent){ //cast refraction ray.
                        castRefractionRay(ray, nonReflectionFactor, collision, hitFromBehind);
                    }else{
                        castShadowRay(nonReflectionFactor, ray, collision);
                    }
                }
        );
        inverseRays.clear();

        // As processing inverserays create new inverse rays which we want to process at a later time we place them in a buffer.
        // And swap the buffer when the rays have been processed.
        swapBuffer();
    }

    private void castShadowRay(double diffuseFactor, InverseRay ray, Collision collision){
        double lightIntensity = diffuseFactor * ray.intensity;

        //TODO: add color filter based on refraction factor. (for reflection and diffuse)
        //for each light create a lightray
        if(lightIntensity > 0.001) { //no need to add the light as it has to little impact on the scene.
            for (PointLight light : pointLights) {
                shadowRays.add(new ShadowRay(
                        light,
                        collision.target,
                        collision.impactPoint,
                        ray,
                        ray.volumeProperties));
            }
        }else{
            System.out.println("Not casting shadow ray. Intensity: " + lightIntensity + " depth: " + ray.depth);
        }
    }

    private void castReflectionRay(InverseRay ray, double reflectionFactor, Collision collision, boolean hitFromBehind){

        //create reflection ray.
        int depth = ray.depth + 1;
        System.out.println("Creating reflection ray. depth: " + depth);
        double intensity = ray.intensity * reflectionFactor;
        if(intensity > 0.001 && depth < 20){ //Only create ray if it has an impact on the scene.
            UnitVector surfaceNormal = (hitFromBehind)?collision.target.surfaceNormal.inverse() : collision.target.surfaceNormal;
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
        }else{
            System.out.println("Not casting reflection ray. Intensity: " + intensity + " depth: " + depth);
        }
    }

    private void castRefractionRay(InverseRay ray, double refractionFactor, Collision collision, boolean hitFromBehind){
        //cast refraction ray.
        int depth = ray.depth + 1;
        System.out.println("Creating refraction ray. depth: " + depth);
        double intensity = ray.intensity * refractionFactor;
        if(intensity > 0.001 && depth < 20){ //no need to add the light as it has to little impact on the scene.
            UnitVector vector;
            if(hitFromBehind){
                vector = calculateRefractionDirection(collision.target.volumeProperties.refractionIndex,
                       1 , ray, collision.target, hitFromBehind);
            }else{
                vector = calculateRefractionDirection(1,
                        collision.target.volumeProperties.refractionIndex, ray, collision.target, hitFromBehind);
            }
            if(vector == null){ // Total internal reflection
                //cast reflection ray
                castReflectionRay(ray, refractionFactor, collision, hitFromBehind);
                System.out.println("Not casting refraction ray. Intensity: " + intensity + " depth: " + depth);
            }else{
                InverseRay inverseRay = new InverseRay(depth, intensity, UnitVector.construct(vector), collision.impactPoint, ray.pixelPosition, collision.target, collision.target.volumeProperties);
                inverseRaysBuffer.add(inverseRay);
            }
        }else{
            System.out.println("Not casting refraction ray. Intensity: " + intensity + " depth: " + depth);
        }
    }

    // Part of the light that is reflected, calculated by base reflection + factor based on angle.
    private double getReflectionFactor(Triangle target, Ray ray, boolean hitFromBehind){
        // initial refractionFactor = (1 - reflectionFactor) * refractionFactor. e.g. (1-0.1) * 0.5 = 0.45
        double reflectionFactor = target.material.reflectionFactor;
        double additionalReflectionFactor;
        if(hitFromBehind){
            //TODO figure out a way to know the refraction index of the volume we are going into.
            additionalReflectionFactor = reflectionFactorCalculator.calculateReflectionFactor(
                    target.surfaceNormal.inverse(), ray.direction.inverse(), target.volumeProperties.refractionIndex, 1);
        }else{
            additionalReflectionFactor = reflectionFactorCalculator.calculateReflectionFactor(
                    target.surfaceNormal, ray.direction.inverse(), 1, target.volumeProperties.refractionIndex);
        }
        double total = (1 - reflectionFactor) * additionalReflectionFactor + reflectionFactor;
        if(total > 1){
            int debug = 0;
        }
        return total;
    }

    private void processShadowRays(){
        shadowRays.stream().forEach(shadowRay ->{
            double diffuseFactor = shadowRay.triangle.surfaceNormal.dot(shadowRay.direction);

            //if light is behind triangle it will not hit.
            if(diffuseFactor < 0) {
                return;
            }

            if(!rayHitsLight(shadowRay)){
                return;
            }

            //calculate color
            UnitVector surfaceNormal = shadowRay.triangle.surfaceNormal;
            UnitVector lightNormal = shadowRay.direction.inverse();
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
            Color color = diffuse.add(specular).scale(shadowRay.inverseRay.intensity);
            //Color color = diffuse.scale(shadowRay.intensity);
            pixelSink.submit(new Pixel(shadowRay.inverseRay.pixelPosition, color));

        });

        shadowRays.clear();
    }

    //Check if shadowRay hits the light.
    private boolean rayHitsLight(ShadowRay shadowRay){

        double distanceToLight = shadowRay.light.position.subtract(shadowRay.position).length();
        for (Triangle triangle : triangles) {
            if (triangle == shadowRay.triangle) {
                //No need to check collision with self.
                continue;
            }
            double collisionDistance = CollisionCalculator.calculateCollisionDistance(triangle, shadowRay);
            if(collisionDistance < 0) continue; //triangle was not hit.
            if (collisionDistance < distanceToLight) {
                return false;
            }
        }
        return true; // No triangles in the way.
    }
}