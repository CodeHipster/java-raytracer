package oostd.am.raytracer.scene.simple.factory;

import oostd.am.raytracer.api.camera.Camera;
import oostd.am.raytracer.api.camera.Color;
import oostd.am.raytracer.api.debug.Window;
import oostd.am.raytracer.api.geography.Dimension;
import oostd.am.raytracer.api.geography.UnitVector;
import oostd.am.raytracer.api.geography.Vector;
import oostd.am.raytracer.api.scenery.ColorFilter;
import oostd.am.raytracer.api.scenery.Material;
import oostd.am.raytracer.api.scenery.PointLight;
import oostd.am.raytracer.api.scenery.Scene;
import oostd.am.raytracer.api.scenery.Triangle;
import oostd.am.raytracer.api.scenery.VolumeProperties;

public class Reflection extends BaseSceneFactory {

    public Reflection() {

        Material material = new Material(
                10,
                1,
                0.5,
                0,
                false,
                new ColorFilter(0, 1, 0)
        );

        //floor
        triangles.add(new Triangle(
                new Vector[]{
                        new Vector(-2000.0, 0.0, 2000.0),
                        new Vector(2000.0, 0.0, 2000.0),
                        new Vector(0.0, 0.0, -2000.0)
                },
                material,
                new VolumeProperties(new ColorFilter(1, 1, 1), 1)
        ));

        // mirror
        triangles.add(new Triangle(
                new Vector[]{
                        new Vector(-2.0, 0.0, 8.0),
                        new Vector(0.0, 4.0, 6.5),
                        new Vector(2.0, 0.0, 5.0)
                },
                new Material(
                        10,
                        0,
                        0,
                        1,
                        false,
                        new ColorFilter(1, 1, 1)),
                new VolumeProperties(new ColorFilter(1, 1, 1), 1)
        ));

        pointLights.add(new PointLight(new Vector(0, 2, 4), new Color(1, 1, 1)));

        Camera renderCamera = new Camera(
                new Vector(0, 5, -10),
                new UnitVector(0, -0.2, 1),
                new UnitVector(0, 1, 0)
                , 1,
                "camera"
        );

        Window debugWindow = new Window(
                new Vector(0, 0, -10),
                new UnitVector(new Vector(0, 0, 1)),
                new UnitVector(new Vector(0, 1, 0)),
                new Dimension(40, 40),
                "behind the camera"
        );
        debugWindows.add(debugWindow);

        Window debugWindow2 = new Window(
                new Vector(10, 0, 0),
                new UnitVector(new Vector(-1, 0, 0)),
                new UnitVector(new Vector(0, 1, 0)),
                new Dimension(40, 40),
                "right, looking left"
        );
        debugWindows.add(debugWindow2);
        Window debugWindow3 = new Window(
                new Vector(-10, 0, 0),
                new UnitVector(new Vector(1, 0, 0)),
                new UnitVector(new Vector(0, 1, 0)),
                new Dimension(40, 40),
                "left, looking right"
        );
        debugWindows.add(debugWindow3);
        Window debugWindow4 = new Window(
                new Vector(0, 10, 0),
                new UnitVector(new Vector(0, -1, 0)),
                new UnitVector(new Vector(0, 0, 1)),
                new Dimension(40, 40),
                "top, looking down"
        );
        debugWindows.add(debugWindow4);
        this.scene = new Scene(triangles, pointLights, renderCamera, debugWindows);
    }
}
