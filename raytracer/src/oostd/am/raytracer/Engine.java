package oostd.am.raytracer;

import oostd.am.raytracer.api.camera.Camera;
import oostd.am.raytracer.api.camera.Pixel;
import oostd.am.raytracer.api.geography.PixelPosition;
import oostd.am.raytracer.api.geography.UnitVector;
import oostd.am.raytracer.api.geography.Vector;
import oostd.am.raytracer.api.scenery.PointLight;
import oostd.am.raytracer.api.scenery.Scene;
import oostd.am.raytracer.api.scenery.Triangle;

import java.util.ArrayList;
import java.util.List;

public class Engine {
    private List<PointLight> pointLights;
    private List<InverseRay> inverseRays = new ArrayList<>();
    private List<LightRay> lightRays = new ArrayList<>();
    private List<Triangle> objects;

    public Engine(Camera camera, Scene scene) {
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
                UnitVector rayDirection = new UnitVector(xpos, ypos, camera.lens.offset);
                inverseRays.add(new InverseRay(1, rayDirection, camera.positioning.position, new PixelPosition(x, y)));
            }
        }
    }

    public void start() {
        for (InverseRay ray : inverseRays) {
            //check collision with triangles;
            for (Triangle triangle : objects) {
                Vector vector = CollisionCalculator.calculateCollision(triangle, ray);
            }
        }
    }
}
