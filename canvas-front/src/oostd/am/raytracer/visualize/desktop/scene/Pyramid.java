package oostd.am.raytracer.visualize.desktop.scene;

import oostd.am.raytracer.api.camera.*;
import oostd.am.raytracer.api.debug.DebugCamera;
import oostd.am.raytracer.api.geography.UnitVector;
import oostd.am.raytracer.api.geography.Vector;
import oostd.am.raytracer.api.scenery.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Flow;

public class Pyramid implements Scene {

    //to be upgraded to a model that partitions
    private List<Triangle> triangles;
    private List<PointLight> pointLights;
    private Camera renderCamera;
    private List<DebugCamera> debugCameras;

    public Pyramid() {
        triangles = new ArrayList<>();
        pointLights = new ArrayList<>();
        setupScene();
    }

    @Override
    public List<Triangle> getTriangles() {
        return triangles;
    }

    @Override
    public List<PointLight> getPointLights() {
        return pointLights;
    }

    @Override
    public Camera getRenderCamera() {
        return renderCamera;
    }

    @Override
    public List<DebugCamera> getDebugCameras() {
        return debugCameras;
    }

    public void attachPixelConsumers(Flow.Subscriber<Pixel> renderOutput, Flow.Subscriber<Pixel> debugOutput){
        debugCameras.get(0).outputConsumer = debugOutput;
        renderCamera.outputConsumer = renderOutput;
    }

    private void setupScene() {
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
                new Vertex[]{
                        new Vertex(-100.0, 0.0, -100.0),
                        new Vertex(0.0, 0.0, 200.0),
                        new Vertex(100.0, 0.0, -100.0)
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
                new Vertex[]{
                        new Vertex(-4.0, 0.0, 0.0),
                        new Vertex(-2.0, 3.0, 2.0),
                        new Vertex(0.0, 0.0, 4.0)
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

        pointLights.add(new PointLight(new Vertex(3, 2, -1), new Color(1, 1, 1)));
        pointLights.add(new PointLight(new Vertex(3, 2, 1), new Color(1, 1, 1)));

        VolumeProperties cameraVolume = new VolumeProperties(new ColorFilter(1, 1, 1), 1);
        renderCamera = new Camera(
                new Positioning(
                        new Vector(0, 2, -10),
                        UnitVector.construct(0, 0, 1))
                ,1
                , new Resolution(500, 500)
                , null
        );

        DebugCamera debugCamRight = new DebugCamera(
                new Positioning(
                        new Vector(10, 0, 0), // moved 10 units to the right
                        UnitVector.construct(-1, 0, 0)) //pointing left
                , 10,10
                , new Resolution(500, 500)
                , null
        );
        debugCameras = new ArrayList<>();
        debugCameras.add(debugCamRight);
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
                        new Vertex[]{
                                new Vertex(-1.0, 0.0, -1.0).translate(position),
                                new Vertex(0.0, 1.0, 0.0).translate(position),
                                new Vertex(1.0, 0.0, -1.0).translate(position)
                        },
                        surface,
                        volume
                )
                ,
                new Triangle(
                        new Vertex[]{
                                new Vertex(1.0, 0.0, -1.0).translate(position),
                                new Vertex(0.0, 1.0, 0.0).translate(position),
                                new Vertex(1.0, 0.0, 1.0).translate(position)
                        },
                        surface,
                        volume
                ),
                new Triangle(
                        new Vertex[]{
                                new Vertex(1.0, 0.0, 1.0).translate(position),
                                new Vertex(0.0, 1.0, 0.0).translate(position),
                                new Vertex(-1.0, 0.0, 1.0).translate(position)
                        },
                        surface,
                        volume
                ),
                new Triangle(
                        new Vertex[]{
                                new Vertex(-1.0, 0.0, 1.0).translate(position),
                                new Vertex(0.0, 1.0, 0.0).translate(position),
                                new Vertex(-1.0, 0.0, -1.0).translate(position)
                        },
                        surface,
                        volume
                )
        };
        return triangles;
    }
}
