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

import java.util.Arrays;

public class Pyramid extends BaseSceneFactory {

    public Pyramid() {
        Material pyramidSurface = new Material(
                100,
                1,
                0,
                false,
                new ColorFilter(1f, 1f, 0.0f)
        );
        VolumeProperties volumeProperties = new VolumeProperties(new ColorFilter(1, 1, 1), 1.2);
        Triangle[] pyramid = createPyramid(pyramidSurface, volumeProperties, new Vector(0, 0, 0));

        Triangle floor = new Triangle(
                new Vector[]{
                        new Vector(-100.0, 0.0, -100.0),
                        new Vector(0.0, 0.0, 200.0),
                        new Vector(100.0, 0.0, -100.0)
                },
                new Material(
                        1000,
                        0.7,
                        0,
                        false,
                        new ColorFilter(1.0f, 0.0f, 0.0f)
                ),
                volumeProperties
        );


        triangles.addAll(Arrays.asList(pyramid));
//        triangles.add(floor);

        pointLights.add(new PointLight(new Vector(3, 2, -1), new Color(1, 1, 1)));

        Camera renderCamera = new Camera(
                new Vector(0, 0.5, -5),
                new UnitVector(0, 0, 1),
                new UnitVector(0, 1, 0),
                1,
                "render");

        Window debugWindow = new Window(
                new Vector(0, 0, -10),
                new UnitVector(new Vector(0, 0, 1)),
                new UnitVector(new Vector(0, 1, 0)),
                new Dimension(20, 20),
                "behind the camera"
        );
        debugWindows.add(debugWindow);

        Window debugWindow2 = new Window(
                new Vector(10, 0, 0),
                new UnitVector(new Vector(-1, 0, 0)),
                new UnitVector(new Vector(0, 1, 0)),
                new Dimension(20, 20),
                "right, looking left"
        );
        debugWindows.add(debugWindow2);
        Window debugWindow3 = new Window(
                new Vector(-10, 0, 0),
                new UnitVector(new Vector(1, 0, 0)),
                new UnitVector(new Vector(0, 1, 0)),
                new Dimension(20, 20),
                "left, looking right"
        );
        debugWindows.add(debugWindow3);
        Window debugWindow4 = new Window(
                new Vector(0, 10, 0),
                new UnitVector(new Vector(0, -1, 0)),
                new UnitVector(new Vector(0, 0, 1)),
                new Dimension(20, 20),
                "top, looking down"
        );
        debugWindows.add(debugWindow4);

        this.scene = new Scene(triangles, pointLights, renderCamera, debugWindows);
    }

    /**
     * @param surface
     * @param volume
     * @param position
     * @return
     */
    private Triangle[] createPyramid(Material surface, VolumeProperties volume, Vector position) {
        Triangle[] triangles = {
                new Triangle(
                        new Vector[]{
                                new Vector(-1.0, 0.0, -1.0).addSelf(position),
                                new Vector(0.0, 1.0, 0.0).addSelf(position),
                                new Vector(1.0, 0.0, -1.0).addSelf(position)
                        },
                        surface,
                        volume
                )
                ,
                new Triangle(
                        new Vector[]{
                                new Vector(1.0, 0.0, -1.0).addSelf(position),
                                new Vector(0.0, 1.0, 0.0).addSelf(position),
                                new Vector(1.0, 0.0, 1.0).addSelf(position)
                        },
                        surface,
                        volume
                ),
                new Triangle(
                        new Vector[]{
                                new Vector(1.0, 0.0, 1.0).addSelf(position),
                                new Vector(0.0, 1.0, 0.0).addSelf(position),
                                new Vector(-1.0, 0.0, 1.0).addSelf(position)
                        },
                        surface,
                        volume
                ),
                new Triangle(
                        new Vector[]{
                                new Vector(-1.0, 0.0, 1.0).addSelf(position),
                                new Vector(0.0, 1.0, 0.0).addSelf(position),
                                new Vector(-1.0, 0.0, -1.0).addSelf(position)
                        },
                        surface,
                        volume
                )
        };
        return triangles;
    }
}
