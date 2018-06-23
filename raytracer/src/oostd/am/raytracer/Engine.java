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

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.SubmissionPublisher;

public class Engine implements Runnable{
    private List<PointLight> pointLights;
    private List<InverseRay> inverseRays = new ArrayList<>();
    private List<LightRay> lightRays = new ArrayList<>();
    private List<Triangle> objects;
    private SubmissionPublisher<Pixel> pixelSink;

    public Engine(Camera camera, Scene scene, SubmissionPublisher<Pixel> pixelSink) {
        this.pixelSink = pixelSink;

        this.pointLights = scene.getPointLights();
        this.objects = scene.getTriangles();

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
                inverseRays.add(new InverseRay(1, rayDirection, camera.positioning.position, new PixelPosition(x, y)));
            }
        }
    }

    @Override
    public void run() {

        for (InverseRay ray : inverseRays) {
            //check collision with triangles;
            for (Triangle triangle : objects) {
                Vector collision = CollisionCalculator.calculateCollision2(triangle, ray);
                if(collision != null){
                    //add ray to lightRays
                    for(PointLight light: pointLights){
                        Vector direction = light.getPosition().subtract(collision); //useless, can be calculated when needed?
                        lightRays.add(new LightRay(light, triangle, UnitVector.construct(direction), collision, ray.getDestination(),ray));
                    }
                }
            }
        }

        for(LightRay ray: lightRays){
            //calculate distance to intersection point, if closer then light, then add no color.

            //check collision with triangles;
            boolean hitLight = true;
            for (Triangle triangle : objects) {
                if(triangle == ray.triangle){
                    continue;
                }
                double collisionDistance = CollisionCalculator.calculateCollisionDistance(triangle, ray);
                Vector rayToLight = ray.position.subtract(ray.light.getPosition());
                double distanceToLight = rayToLight.length();
                if(collisionDistance < distanceToLight){
                    hitLight = false;
                    break;
                }
            }
            if(hitLight){
                //calculate color
                UnitVector lightNormal = UnitVector.construct(ray.position.subtract(ray.light.getPosition()));
                UnitVector inverseLightNormal = lightNormal.inverse();
                UnitVector surfaceNormal = ray.triangle.surfaceNormal;
                UnitVector reflectNormal = lightNormal.reflectOn(surfaceNormal);
                UnitVector inverseViewNormal = ray.predecessor.direction.inverse();

                double diffuseFactor = surfaceNormal.dot(inverseLightNormal);

                double specularIntensity = 1;
                double specularPower = 100;
                double specularFactor = reflectNormal.dot(inverseViewNormal);
                specularFactor = Math.pow(specularFactor,specularPower);
                Color specular = ray.light.color.clone().scale(specularIntensity*specularFactor);

                Color diffuse = ray.triangle.colorFilter.filter(ray.light.color.clone()).scale(diffuseFactor);

                pixelSink.submit(new Pixel(ray.getDestination(), diffuse.add(specular)));
            }
        }
        pixelSink.close();
    }
}