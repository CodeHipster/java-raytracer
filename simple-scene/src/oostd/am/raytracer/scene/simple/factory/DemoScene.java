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
import oostd.am.raytracer.api.scenery.Sphere;
import oostd.am.raytracer.api.scenery.Triangle;
import oostd.am.raytracer.api.scenery.VolumeProperties;

import java.util.Arrays;

public class DemoScene extends BaseSceneFactory {

    public DemoScene() {
        VolumeProperties volumeProperties = new VolumeProperties(new ColorFilter(1, 1, 1), 1.5);

        Sphere transparentSphere = new Sphere(new Vector(20, 15, 10), 15, new Material(
                100,
                0,
                1,
                0,
                true,
                new ColorFilter(1f, 1f, 1f)), volumeProperties);
        spheres.add(transparentSphere);

        Sphere diffuseSphere = new Sphere(new Vector(-40, 4, 50), 4, new Material(
                100,
                0.8,
                1,
                0.2,
                false,
                new ColorFilter(0.5f, 0, 0.5f)), volumeProperties);
//        spheres.add(diffuseSphere);

        Sphere diffuseSphere2 = new Sphere(new Vector(-40, 4, 70), 4, new Material(
                100,
                0.8,
                1,
                0.2,
                false,
                new ColorFilter(1f, 0, 0f)), volumeProperties);
//        spheres.add(diffuseSphere2);

        Sphere diffuseSphere3 = new Sphere(new Vector(-10, 5, -10), 5, new Material(
                100,
                0.8,
                1,
                0.2,
                false,
                new ColorFilter(0f, 1, 0f)), volumeProperties);
        spheres.add(diffuseSphere3);

        Triangle[] pyramid = createPyramid(new Material(
                        10,
                        1,
                        1,
                        0,
                        false,
                        new ColorFilter(0.506f, 0.694f, 0.694f)),
                volumeProperties,
                new Vector(-60, 0, 60),
                10);
//        triangles.addAll(Arrays.asList(pyramid));

        Triangle[] mirror = createPlane(
                new Vector(60, 0, 30),
                70,
                new UnitVector(-1, 0 , -1),
                new Material(
                        10,
                        0,
                        1,
                        1,
                        false,
                        new ColorFilter(1f, 1f, 1f))
                , volumeProperties
        );
        triangles.addAll(Arrays.asList(mirror));

        Triangle[] room = createBox(new Vector(0, 5000, 0), 5000);
        triangles.addAll(Arrays.asList(room));

        pointLights.add(new PointLight(new Vector(-1000, 500, 200), new Color(1, 1, 1)));

        Camera renderCamera = new Camera(
                new Vector(0, 40, -80),
                new UnitVector(0, -0.2, 1),
                new UnitVector(0, 1, 0)
                , 0.7,
                "camera"
        );

        Window debugWindow = new Window(
                new Vector(0, 0, -50),
                new UnitVector(new Vector(0, 0, 1)),
                new UnitVector(new Vector(0, 1, 0)),
                new Dimension(200, 200),
                "behind the camera"
        );
//        debugWindows.add(debugWindow);

        Window debugWindow2 = new Window(
                new Vector(50, 0, 0),
                new UnitVector(new Vector(-1, 0, 0)),
                new UnitVector(new Vector(0, 1, 0)),
                new Dimension(200, 200),
                "right, looking left"
        );
//        debugWindows.add(debugWindow2);
        Window debugWindow3 = new Window(
                new Vector(-50, 0, 0),
                new UnitVector(new Vector(1, 0, 0)),
                new UnitVector(new Vector(0, 1, 0)),
                new Dimension(200, 200),
                "left, looking right"
        );
        debugWindows.add(debugWindow3);
        Window debugWindow4 = new Window(
                new Vector(0, 50, 0),
                new UnitVector(new Vector(0, -1, 0)),
                new UnitVector(new Vector(0, 0, 1)),
                new Dimension(200, 200),
                "top, looking down"
        );
        debugWindows.add(debugWindow4);

        this.scene = new Scene(triangles, spheres, pointLights, renderCamera, debugWindows);
    }

    private Triangle[] createPlane(Vector position, double size, UnitVector normal, Material surface, VolumeProperties volume) {

        Vector yAxis = new Vector(0, 1, 0);
        UnitVector xAxis = normal.cross(yAxis).unit();
        Vector triangle1point1 = xAxis.scale(-0.5);
        Vector triangle1point2 = triangle1point1.add(yAxis);
        Vector triangle1point3 = xAxis.scale(0.5);

        Vector triangle2point1 = new Vector(0,0,0).addSelf(triangle1point2);
        Vector triangle2point2 = triangle1point2.add(xAxis);
        Vector triangle2point3 = new Vector(0,0,0).addSelf(triangle1point3);

        return new Triangle[]{
                new Triangle(
                        new Vector[]{
                                triangle1point1.scaleSelf(size).addSelf(position), triangle1point2.scaleSelf(size).addSelf(position), triangle1point3.scaleSelf(size).addSelf(position)
                        },
                        surface,
                        volume
                ),
                new Triangle(
                        new Vector[]{
                                triangle2point1.scaleSelf(size).addSelf(position), triangle2point2.scaleSelf(size).addSelf(position), triangle2point3.scaleSelf(size).addSelf(position)
                        },
                        surface,
                        volume
                )
        };
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
                                new ColorFilter(0.529f, 0.808f, 0.922f)),
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
                                new ColorFilter(0.529f, 0.808f, 0.922f)),
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
                                new ColorFilter(0.529f, 0.808f, 0.922f)),
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
                                new ColorFilter(0.529f, 0.808f, 0.922f)),
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
                                100,
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
                                new ColorFilter(0.529f, 0.808f, 0.922f)),
                        new VolumeProperties(new ColorFilter(1, 1, 1), 1)
                )
        };
        return triangles;
    }

    private Triangle[] createTetraHedron(Vector position, double size, Material surface, VolumeProperties volume) {
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
    private Triangle[] createPyramid(Material surface, VolumeProperties volume, Vector position, int size) {
        Triangle[] triangles = {
                new Triangle(
                        new Vector[]{
                                new Vector(-3.0, 0.0, -3.0).scaleSelf(size).addSelf(position),
                                new Vector(0.0, 3.0, 0.0).scaleSelf(size).addSelf(position),
                                new Vector(3.0, 0.0, -3.0).scaleSelf(size).addSelf(position)
                        },
                        surface,
                        volume
                )
                ,
                new Triangle(
                        new Vector[]{
                                new Vector(3.0, 0.0, -3.0).scaleSelf(size).addSelf(position),
                                new Vector(0.0, 3.0, 0.0).scaleSelf(size).addSelf(position),
                                new Vector(3.0, 0.0, 3.0).scaleSelf(size).addSelf(position)
                        },
                        surface,
                        volume
                ),
                new Triangle(
                        new Vector[]{
                                new Vector(3.0, 0.0, 3.0).scaleSelf(size).addSelf(position),
                                new Vector(0.0, 3.0, 0.0).scaleSelf(size).addSelf(position),
                                new Vector(-3.0, 0.0, 3.0).scaleSelf(size).addSelf(position)
                        },
                        surface,
                        volume
                ),
                new Triangle(
                        new Vector[]{
                                new Vector(-3.0, 0.0, 3.0).scaleSelf(size).addSelf(position),
                                new Vector(0.0, 3.0, 0.0).scaleSelf(size).addSelf(position),
                                new Vector(-3.0, 0.0, -3.0).scaleSelf(size).addSelf(position)
                        },
                        surface,
                        volume
                )
        };
        return triangles;
    }
}
