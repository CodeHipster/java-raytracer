package oostd.am.raytracer;

import oostd.am.raytracer.api.camera.Camera;
import oostd.am.raytracer.api.camera.Lens;
import oostd.am.raytracer.api.camera.Positioning;
import oostd.am.raytracer.api.geography.UnitVector;
import oostd.am.raytracer.api.geography.Vector;
import oostd.am.raytracer.api.scenery.PointLight;
import oostd.am.raytracer.api.scenery.Scene;
import oostd.am.raytracer.api.scenery.Triangle;
import oostd.am.raytracer.api.scenery.Vertex;
import org.junit.jupiter.api.Test;

import java.util.Arrays;


public class EngineTest {

    @Test
    public void start() {
        Scene scene = new Scene(
                Arrays.asList(
                        new Triangle(new Vertex[]{
                                new Vertex(-1.5f, 2, 12),
                                new Vertex(1, 1, 11),
                                new Vertex(-1, -1, 10)
                        })),
                Arrays.asList(new PointLight(new Vertex(0, 5, 8))));
        Camera camera = new Camera(
                new Positioning(
                        new Vector(0, 0, 0), //Camera at the center of the scene
                        new UnitVector(0, 0, 1)) //Camera pointing 'forward' into the scene
                , new Lens(300, 300, 1));
        Engine engine = new Engine(camera, scene);
        engine.start();
    }
}