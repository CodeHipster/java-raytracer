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

public class TetraHedron extends BaseSceneFactory {

    public TetraHedron() {
        Material tetraHedronSurface = new Material(
                100,
                0,
                1,
                0,
                true,
                new ColorFilter(1f, 1f, 1.0f)
        );
        VolumeProperties volumeProperties = new VolumeProperties(new ColorFilter(1, 1, 1), 1.1);
        Triangle[] prism = createTetraHedron(new Vector(0, 0, 0), 6, tetraHedronSurface, volumeProperties);

        Triangle[] room = createBox(new Vector(0,0,0), 20);

        triangles.addAll(Arrays.asList(room));
        triangles.addAll(Arrays.asList(prism));
//
        pointLights.add(new PointLight(new Vector(-15, 8, 15), new Color(0.5, 0.5, 0.5)));
        pointLights.add(new PointLight(new Vector(15, -8, -15), new Color(0.5, 0.5, 0.5)));
//        pointLights.add(new PointLight(new Vector(4, 4, 4), new Color(1, 1, 1)));
//        pointLights.add(new PointLight(new Vector(4, 4, -4), new Color(1, 1, 1)));
//        pointLights.add(new PointLight(new Vector(0, 8, 0), new Color(1, 1, 1)));

        Camera renderCamera = new Camera(
                new Vector(4, 5, -19),
//                new UnitVector(-1, -1, 1),
                new UnitVector(-0.2, -0.3, 1),
                new UnitVector(0, 1, 0)
                , .6,
                "camera"
        );

        Window debugWindow = new Window(
                new Vector(0, 0, -50),
                new UnitVector(new Vector(0, 0, 1)),
                new UnitVector(new Vector(0, 1, 0)),
                new Dimension(50, 50),
                "behind the camera"
        );
        debugWindows.add(debugWindow);

        Window debugWindow2 = new Window(
                new Vector(50, 0, 0),
                new UnitVector(new Vector(-1, 0, 0)),
                new UnitVector(new Vector(0, 1, 0)),
                new Dimension(50, 50),
                "right, looking left"
        );
        debugWindows.add(debugWindow2);
        Window debugWindow3 = new Window(
                new Vector(-50, 0, 0),
                new UnitVector(new Vector(1, 0, 0)),
                new UnitVector(new Vector(0, 1, 0)),
                new Dimension(50, 50),
                "left, looking right"
        );
        debugWindows.add(debugWindow3);
        Window debugWindow4 = new Window(
                new Vector(0, 50, 0),
                new UnitVector(new Vector(0, -1, 0)),
                new UnitVector(new Vector(0, 0, 1)),
                new Dimension(50, 50),
                "top, looking down"
        );
        debugWindows.add(debugWindow4);

        this.scene = new Scene(triangles, spheres, pointLights, renderCamera, debugWindows);
    }

    private Triangle[] createBox(Vector position, double size) {
        Triangle[] triangles = {
                // backwall
                new Triangle(
                        new Vector[]{
                                new Vector(-400.0, -400.0, 1).scaleSelf(size).addSelf(position),
                                new Vector(0.0, 400.0, 1).scaleSelf(size).addSelf(position),
                                new Vector(400.0, -400.0, 1).scaleSelf(size).addSelf(position)
                        },
                        new Material(
                                10,
                                1,
                                0.5,
                                0,
                                false,
                                new ColorFilter(0.5f, 1, 0.5f)),
                        new VolumeProperties(new ColorFilter(1, 1, 1), 1)
                ),

                // right wall
                new Triangle(
                        new Vector[]{
                                new Vector(1, -400.0, 400.0).scaleSelf(size).addSelf(position),
                                new Vector(1, 400.0, 0.0).scaleSelf(size).addSelf(position),
                                new Vector(1, -400, -400.0).scaleSelf(size).addSelf(position)
                        },
                        new Material(
                                10,
                                1,
                                0.5,
                                0,
                                false,
                                new ColorFilter(0.5f, 0.5f, 1f)),
                        new VolumeProperties(new ColorFilter(1, 1, 1), 1)
                ),


                // left wall
                new Triangle(
                        new Vector[]{
                                new Vector(-1, -400.0, -400.0).scaleSelf(size).addSelf(position),
                                new Vector(-1, 400.0, 0.0).scaleSelf(size).addSelf(position),
                                new Vector(-1, -400, 400.0).scaleSelf(size).addSelf(position)
                        },
                        new Material(
                                10,
                                1,
                                0.5,
                                0,
                                false,
                                new ColorFilter(1f, 0.5f, 0.5f)),
                        new VolumeProperties(new ColorFilter(1, 1, 1), 1)
                ),

                // frontWall, behind camera
                new Triangle(
                        new Vector[]{
                                new Vector(400.0, -400.0, -1).scaleSelf(size).addSelf(position),
                                new Vector(0.0, 400.0, -1).scaleSelf(size).addSelf(position),
                                new Vector(-400.0, -400.0, -1).scaleSelf(size).addSelf(position)
                        },
                        new Material(
                                10,
                                1,
                                0.5,
                                0,
                                false,
                                new ColorFilter(0.5f, 1, 0.5f)),
                        new VolumeProperties(new ColorFilter(1, 1, 1), 1)
                ),

                //floor
                new Triangle(
                        new Vector[]{
                                new Vector(-1000.0, -1, -1000.0).scaleSelf(size).addSelf(position),
                                new Vector(0.0, -1, 1000.0).scaleSelf(size).addSelf(position),
                                new Vector(1000.0, -1, -1000.0).scaleSelf(size).addSelf(position)
                        },
                        new Material(
                                10,
                                1,
                                0.5,
                                0,
                                false,
                                new ColorFilter(0.5f, 0.5f, 0.5f)
                        ),
                        new VolumeProperties(new ColorFilter(1, 1, 1), 1)
                ),

                //ceiling
                new Triangle(
                        new Vector[]{
                                new Vector(-1000.0, 1, -1000.0).scaleSelf(size).addSelf(position),
                                new Vector(1000.0, 1, -1000.0).scaleSelf(size).addSelf(position),
                                new Vector(0.0, 1, 1000.0).scaleSelf(size).addSelf(position)
                        },
                        new Material(
                                10,
                                1,
                                0.5,
                                0,
                                false,
                                new ColorFilter(0.1f, 0.3f, 0.6f)
                        ),
                        new VolumeProperties(new ColorFilter(1, 1, 1), 1)
                )
        };
        return triangles;
    }

    private Triangle[] createTetraHedron(Vector position, double size, Material surface, VolumeProperties volume){
        Triangle[] triangles = {
                //Bottom
                //TODO: make proper tetra hedron, with same angles
                new Triangle(
                        new Vector[]{
                                new Vector(-1.0, -1.0, -1.0).scaleSelf(size).addSelf(position),
                                new Vector(1.0, -1.0, -1.0).scaleSelf(size).addSelf(position),
                                new Vector(0.0, -1.0, 1.0).scaleSelf(size).addSelf(position)
                        },
                        surface,
                        volume
                )
                ,
                //front
                new Triangle(
                        new Vector[]{
                                new Vector(-1.0, -1.0, -1.0).scaleSelf(size).addSelf(position),
                                new Vector(0.0, 1.0, 0.0).scaleSelf(size).addSelf(position),
                                new Vector(1.0, -1.0, -1.0).scaleSelf(size).addSelf(position)
                        },
                        surface,
                        volume
                ),
                //left
                new Triangle(
                        new Vector[]{
                                new Vector(0.0, -1.0, 1.0).scaleSelf(size).addSelf(position),
                                new Vector(0.0, 1.0, 0.0).scaleSelf(size).addSelf(position),
                                new Vector(-1.0, -1.0, -1.0).scaleSelf(size).addSelf(position)
                        },
                        surface,
                        volume
                ),
                new Triangle(
                        new Vector[]{
                                new Vector(1.0, -1.0, -1.0).scaleSelf(size).addSelf(position),
                                new Vector(0.0, 1.0, 0.0).scaleSelf(size).addSelf(position),
                                new Vector(0.0, -1.0, 1.0).scaleSelf(size).addSelf(position)
                        },
                        surface,
                        volume
                )
        };
        return triangles;
    }

    private Triangle[] createOktaHedron(Vector position, double size, Material surface, VolumeProperties volume) {
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
