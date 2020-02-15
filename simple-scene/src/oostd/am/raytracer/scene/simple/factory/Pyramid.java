package oostd.am.raytracer.scene.simple.factory;

import oostd.am.raytracer.api.camera.Camera;
import oostd.am.raytracer.api.camera.Color;
import oostd.am.raytracer.api.camera.Positioning;
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
                new ColorFilter(0.5f, 0.5f, 0.0f)
        );
        VolumeProperties volumeProperties = new VolumeProperties(new ColorFilter(1, 1, 1), 1.2);
        Triangle[] pyramid = createPyramid(pyramidSurface, volumeProperties, new Vector(0, 0, 0));

        Material pyramidTransparentSurface = new Material(
                100,
                1,
                0,
                true,
                new ColorFilter(1f, 1f, 1f)
        );
        Triangle[] transparentPyramid = createPyramid(pyramidTransparentSurface, volumeProperties, new Vector(0, 0, -2));

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
        Triangle mirror = new Triangle(
                new Vector[]{
                        new Vector(-4.0, 0.0, 0.0),
                        new Vector(-2.0, 3.0, 2.0),
                        new Vector(0.0, 0.0, 4.0)
                },
                new Material(
                        10000,
                        2,
                        0.9,
                        false,
                        new ColorFilter(0.5f, 0.5f, 0.5f)
                ),
                volumeProperties
        );

        triangles.addAll(Arrays.asList(pyramid));
        triangles.add(floor);
        triangles.add(mirror);

        pointLights.add(new PointLight(new Vector(3, 2, -1), new Color(1, 1, 1)));
        pointLights.add(new PointLight(new Vector(3, 2, 1), new Color(1, 1, 1)));

        Camera renderCamera = new Camera(
                new Positioning(
                        new Vector(0, 2, -10),
                        UnitVector.construct(0, 0, 1))
                , 1);

        Window debugWindow = new Window(
                new Vector(0, 0, 0),
                UnitVector.construct(new Vector(1, 0, 0)),
                UnitVector.construct(new Vector(0, 1, 0)),
                new Dimension(10, 10)
        );
        debugWindows.add(debugWindow);
        
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
