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

public class Refraction extends BaseSceneFactory {

    public Refraction() {
        Material prismSurface = new Material(
                100,
                0,
                1,
                0,
                true,
                new ColorFilter(1f, 1f, 1.0f)
        );
        VolumeProperties volumeProperties = new VolumeProperties(new ColorFilter(1, 1, 1), 1.2);
        Triangle[] prism = createPrism(new Vector(0,5, 0), 3, prismSurface, volumeProperties);

        // backwall
        triangles.add(new Triangle(
                new Vector[]{
                        new Vector(-40.0, 0.0, 9.0),
                        new Vector(0.0, 40.0, 9.0),
                        new Vector(40.0, 0.0, 9.0)
                },
                new Material(
                        10,
                        1,
                        0.5,
                        0,
                        false,
                        new ColorFilter(0, 1, 0)),
                new VolumeProperties(new ColorFilter(1, 1, 1), 1)
        ));

        Triangle floor = new Triangle(
                new Vector[]{
                        new Vector(-1000.0, 0.0, -1000.0),
                        new Vector(0.0, 0.0, 1000.0),
                        new Vector(1000.0, 0.0, -1000.0)
                },
                new Material(
                        256,
                        1,
                        0.7,
                        0,
                        false,
                        new ColorFilter(1.0f, 0.0f, 0.0f)
                ),
                volumeProperties
        );


        triangles.addAll(Arrays.asList(prism));
        triangles.add(floor);

        pointLights.add(new PointLight(new Vector(-4, 4, 4), new Color(1, 1, 1)));
        pointLights.add(new PointLight(new Vector(-4, 4, -4), new Color(1, 1, 1)));
        pointLights.add(new PointLight(new Vector(4, 4, 4), new Color(1, 1, 1)));
        pointLights.add(new PointLight(new Vector(4, 4, -4), new Color(1, 1, 1)));

        Camera renderCamera = new Camera(
                new Vector(0, 8, -10),
                new UnitVector(0, -0.1, 1),
                new UnitVector(0, 1, 0)
                , .7,
                "camera"
        );

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

        this.scene = new Scene(triangles, spheres, pointLights, renderCamera, debugWindows);
    }

    private Triangle[] createPrism(Vector position, double size, Material surface, VolumeProperties volume){
        Triangle[] triangles = {
                new Triangle(
                        new Vector[]{
                                new Vector(-1.0, 0.0, -1.0).scaleSelf(size).addSelf(position),
                                new Vector(0.0, 1.0, 0.0).scaleSelf(size).addSelf(position),
                                new Vector(1.0, 0.0, -1.0).scaleSelf(size).addSelf(position)
                        },
                        surface,
                        volume
                )
                ,
                new Triangle(
                        new Vector[]{
                                new Vector(1.0, 0.0, -1.0).scaleSelf(size).addSelf(position),
                                new Vector(0.0, 1.0, 0.0).scaleSelf(size).addSelf(position),
                                new Vector(1.0, 0.0, 1.0).scaleSelf(size).addSelf(position)
                        },
                        surface,
                        volume
                ),
                new Triangle(
                        new Vector[]{
                                new Vector(1.0, 0.0, 1.0).scaleSelf(size).addSelf(position),
                                new Vector(0.0, 1.0, 0.0).scaleSelf(size).addSelf(position),
                                new Vector(-1.0, 0.0, 1.0).scaleSelf(size).addSelf(position)
                        },
                        surface,
                        volume
                ),
                new Triangle(
                        new Vector[]{
                                new Vector(-1.0, 0.0, 1.0).scaleSelf(size).addSelf(position),
                                new Vector(0.0, 1.0, 0.0).scaleSelf(size).addSelf(position),
                                new Vector(-1.0, 0.0, -1.0).scaleSelf(size).addSelf(position)
                        },
                        surface,
                        volume
                ),
                //bottom half
                new Triangle(
                        new Vector[]{
                                new Vector(0.0, -1.0, 0.0).scaleSelf(size).addSelf(position),
                                new Vector(-1.0, 0.0, -1.0).scaleSelf(size).addSelf(position),
                                new Vector(1.0, 0.0, -1.0).scaleSelf(size).addSelf(position)
                        },
                        surface,
                        volume
                )
                ,
                new Triangle(
                        new Vector[]{
                                new Vector(0.0, -1.0, 0.0).scaleSelf(size).addSelf(position),
                                new Vector(1.0, 0.0, -1.0).scaleSelf(size).addSelf(position),
                                new Vector(1.0, 0.0, 1.0).scaleSelf(size).addSelf(position)
                        },
                        surface,
                        volume
                ),
                new Triangle(
                        new Vector[]{
                                new Vector(0.0, -1.0, 0.0).scaleSelf(size).addSelf(position),
                                new Vector(1.0, 0.0, 1.0).scaleSelf(size).addSelf(position),
                                new Vector(-1.0, 0.0, 1.0).scaleSelf(size).addSelf(position)
                        },
                        surface,
                        volume
                ),
                new Triangle(
                        new Vector[]{
                                new Vector(0.0, -1.0, 0.0).scaleSelf(size).addSelf(position),
                                new Vector(-1.0, 0.0, 1.0).scaleSelf(size).addSelf(position),
                                new Vector(-1.0, 0.0, -1.0).scaleSelf(size).addSelf(position)
                        },
                        surface,
                        volume
                )
        };
        return triangles;
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
                                new Vector(-3.0, 0.0, -3.0).addSelf(position),
                                new Vector(0.0, 3.0, 0.0).addSelf(position),
                                new Vector(3.0, 0.0, -3.0).addSelf(position)
                        },
                        surface,
                        volume
                )
                ,
                new Triangle(
                        new Vector[]{
                                new Vector(3.0, 0.0, -3.0).addSelf(position),
                                new Vector(0.0, 3.0, 0.0).addSelf(position),
                                new Vector(3.0, 0.0, 3.0).addSelf(position)
                        },
                        surface,
                        volume
                ),
                new Triangle(
                        new Vector[]{
                                new Vector(3.0, 0.0, 3.0).addSelf(position),
                                new Vector(0.0, 3.0, 0.0).addSelf(position),
                                new Vector(-3.0, 0.0, 3.0).addSelf(position)
                        },
                        surface,
                        volume
                ),
                new Triangle(
                        new Vector[]{
                                new Vector(-3.0, 0.0, 3.0).addSelf(position),
                                new Vector(0.0, 3.0, 0.0).addSelf(position),
                                new Vector(-3.0, 0.0, -3.0).addSelf(position)
                        },
                        surface,
                        volume
                )
        };
        return triangles;
    }
}
