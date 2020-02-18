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

public class Simple extends BaseSceneFactory {

    public Simple() {

        Material material = new Material(
                100,
                1,
                0,
                false,
                new ColorFilter(0.5f, 0.5f, 0.0f)
        );

        triangles.add(new Triangle(
                new Vector[]{
                        new Vector(-7.0, 0.0, -2.0),
                        new Vector(0.0, 5.0, 0.0),
                        new Vector(2.0, -3.0, 2.0)
                },
                material,
                new VolumeProperties(new ColorFilter(1, 1, 1), 1)
        ));
        pointLights.add(new PointLight(new Vector(3, 2, 1), new Color(1, 1, 1)));

        Camera renderCamera = new Camera(
                new Vector(0, 2, -10),
                new UnitVector(0, 0, 1),
                new UnitVector(0, 1, 0)
                , 1
        );

        Window debugWindow = new Window(
                new Vector(0, 0, -10),
                new UnitVector(new Vector(0, 0, 1)),
                new UnitVector(new Vector(0, 1, 0)),
                new Dimension(20, 20)
        );
        debugWindows.add(debugWindow);

        Window debugWindow2 = new Window(
                new Vector(10, 0, 0),
                new UnitVector(new Vector(-1, 0, 0)),
                new UnitVector(new Vector(0, 1, 0)),
                new Dimension(20, 20)
        );
        debugWindows.add(debugWindow2);
        this.scene = new Scene(triangles, pointLights, renderCamera, debugWindows);
    }
}
