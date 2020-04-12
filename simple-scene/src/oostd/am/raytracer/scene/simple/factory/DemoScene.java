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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DemoScene extends BaseSceneFactory {

    public DemoScene() {
        VolumeProperties volumeProperties = new VolumeProperties(new ColorFilter(1, 1, 1), 1.5);

        Triangle[] room = createBox(new Vector(0, 5000, 0), 5000);
        triangles.addAll(Arrays.asList(room));

        Triangle[] mirror = createPlate(
                new Vector(60, 35+2.5, 30),
                70,
                new UnitVector(-1, 0, -1),
                new UnitVector(0,1,0),
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

        Triangle[] frame = createPlate(
                new Vector(60.5, 35+2.5, 30.5),
                75,
                new UnitVector(-1, 0, -1),
                new UnitVector(0,1,0),
                new Material(
                        1000,
                        1,
                        1,
                        0,
                        false,
                        new ColorFilter(0.627f, 0.321f, 0.176f))
                , volumeProperties
        );
        triangles.addAll(Arrays.asList(frame));

        Triangle[] mirror2 = createPlate(
                new Vector(-60, 35+2.5, -30),
                70,
                new UnitVector(1, 0, 1),
                new UnitVector(0,1,0),
                new Material(
                        10,
                        0,
                        1,
                        1,
                        false,
                        new ColorFilter(1f, 1f, 1f))
                , volumeProperties
        );
        triangles.addAll(Arrays.asList(mirror2));

        Triangle[] frame2 = createPlate(
                new Vector(-60.5, 35+2.5, -30.5),
                75,
                new UnitVector(1, 0, 1),
                new UnitVector(0,1,0),
                new Material(
                        1,
                        1,
                        1,
                        0,
                        false,
                        new ColorFilter(1f, 1f, 1f))
                , volumeProperties
        );
        triangles.addAll(Arrays.asList(frame2));

        Sphere transparentSphere = new Sphere(new Vector(20, 15, 10), 15, new Material(
                100,
                0,
                1,
                0,
                true,
                new ColorFilter(1f, 1f, 1f)), volumeProperties);
        spheres.add(transparentSphere);

        Sphere diffuseSphere = new Sphere(new Vector(-10, 5, -10), 5, new Material(
                100,
                0.8,
                1,
                0.2,
                false,
                new ColorFilter(0f, 1, 0f)), volumeProperties);
        spheres.add(diffuseSphere);

        Triangle[] cubeRed = createCube(new Vector(-30, 5, -30), 10, new UnitVector(-1,0,-1), new UnitVector(0,1,0),
                new Material(
                100,
                0.9,
                1,
                0.1,
                false,
                new ColorFilter(1, 0, 0)), volumeProperties);
        triangles.addAll(Arrays.asList(cubeRed));

        Triangle[] cubeGreen = createCube(new Vector(-10, 5, -30), 10, new UnitVector(-1,0,-1), new UnitVector(0,1,0),
                new Material(
                        100,
                        0.9,
                        1,
                        0.1,
                        false,
                        new ColorFilter(0, 1, 0)), volumeProperties);
        triangles.addAll(Arrays.asList(cubeGreen));

        Triangle[] cubeBlue = createCube(new Vector(10, 5, -30), 10, new UnitVector(-1,0,-1), new UnitVector(0,1,0),
                new Material(
                        100,
                        0.9,
                        1,
                        0.1,
                        false,
                        new ColorFilter(0, 0, 1)), volumeProperties);
        triangles.addAll(Arrays.asList(cubeBlue));

        Triangle[] pyramid = createPyramid(new Material(
                        10,
                        1,
                        1,
                        0,
                        false,
                        new ColorFilter(0.506f, 0.694f, 0.694f)),
                volumeProperties,
                new Vector(-400, 0, 800),
                50);
        triangles.addAll(Arrays.asList(pyramid));

        Triangle[] cubeWhite1 = createCube(new Vector(-50, 5, 40), 10, new UnitVector(-1,0,0), new UnitVector(0,1,0),
                new Material(
                        100,
                        0.9,
                        1,
                        0.1,
                        false,
                        new ColorFilter(1, 1, 1)), volumeProperties);
        triangles.addAll(Arrays.asList(cubeWhite1));

        Triangle[] cubeWhite2 = createCube(new Vector(-30, 5, 40), 10, new UnitVector(-1,0,0), new UnitVector(0,1,0),
                new Material(
                        100,
                        0.9,
                        1,
                        0.1,
                        false,
                        new ColorFilter(1, 1, 1)), volumeProperties);
        triangles.addAll(Arrays.asList(cubeWhite2));

        Triangle[] cubeWhite3 = createCube(new Vector(-10, 5, 40), 10, new UnitVector(-1,0,0), new UnitVector(0,1,0),
                new Material(
                        100,
                        0.9,
                        1,
                        0.1,
                        false,
                        new ColorFilter(1, 1, 1)), volumeProperties);
        triangles.addAll(Arrays.asList(cubeWhite3));


        pointLights.add(new PointLight(new Vector(-1000, 500, 200), new Color(0.5, 0.5, 0.5)));
        pointLights.add(new PointLight(new Vector(0, 500, -100), new Color(0.5, 0.5, 0.5)));



        Sphere redSphere = new Sphere(new Vector(-50, 21, 26), 0.8, new Material(
                100,
                1,
                1,
                0,
                false,
                new ColorFilter(1f, 0, 0)), volumeProperties);
        spheres.add(redSphere);
        pointLights.add(new PointLight(new Vector(-50, 20, 25), new Color(.5, 0, 0)));
        Sphere greenSphere = new Sphere(new Vector(-30, 21, 26), 0.8, new Material(
                100,
                1,
                1,
                0,
                false,
                new ColorFilter(0, 1f, 0)), volumeProperties);
        spheres.add(greenSphere);
        pointLights.add(new PointLight(new Vector(-30, 20, 25), new Color(0, .5, 0)));
        Sphere blueSphere = new Sphere(new Vector(-10, 21, 26), 0.8, new Material(
                100,
                1,
                1,
                0,
                false,
                new ColorFilter(0, 0, 1f)), volumeProperties);
        spheres.add(blueSphere);
        pointLights.add(new PointLight(new Vector(-10, 20, 25), new Color(0, 0, .5)));

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

    private Triangle[] createPlate(Vector position, double size, UnitVector normal, UnitVector up, Material surface, VolumeProperties volume) {

        UnitVector xAxis = normal.cross(up).unit();
        Vector triangle1point1 = xAxis.scale(-0.5).add(up.scale(-0.5));
        Vector triangle1point2 = triangle1point1.add(up);
        Vector triangle1point3 = triangle1point1.add(xAxis);

        Vector triangle2point1 = new Vector(0, 0, 0).addSelf(triangle1point2);
        Vector triangle2point2 = triangle1point2.add(xAxis);
        Vector triangle2point3 = new Vector(0, 0, 0).addSelf(triangle1point3);

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

    private Triangle[] createCube(Vector position, double size, UnitVector normal, UnitVector up, Material surface, VolumeProperties volume) {

        List<Triangle> triangles = new ArrayList<>();
        UnitVector left = normal.cross(up).unit();

        //front
        triangles.addAll(Arrays.asList(createPlate(position.add(normal.scale(0.5*size)), size, normal, up, surface, volume)));

        //back
        triangles.addAll(Arrays.asList(createPlate(position.add(normal.scale(-0.5*size)), size, normal.invert(), up, surface, volume)));
        //left
        triangles.addAll(Arrays.asList(createPlate(position.add(left.scale(0.5*size)), size, left, up, surface, volume)));
        //right
        triangles.addAll(Arrays.asList(createPlate(position.add(left.scale(-0.5*size)), size, left.invert(), up, surface, volume)));
        //top
        triangles.addAll(Arrays.asList(createPlate(position.add(up.scale(0.5*size)), size, up, normal, surface, volume)));
        //bottom
        triangles.addAll(Arrays.asList(createPlate(position.add(up.scale(-0.5*size)), size, up.invert(), normal, surface, volume)));

        return triangles.toArray(Triangle[]::new);

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
