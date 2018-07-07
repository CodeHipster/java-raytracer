package oostd.am.raytracer.visualize.desktop.menu;

import oostd.am.raytracer.api.camera.Camera;
import oostd.am.raytracer.api.camera.Color;
import oostd.am.raytracer.api.camera.Lens;
import oostd.am.raytracer.api.camera.Positioning;
import oostd.am.raytracer.api.geography.UnitVector;
import oostd.am.raytracer.api.geography.Vector;
import oostd.am.raytracer.api.scenery.*;
import oostd.am.raytracer.visualize.desktop.Service;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MenuListener implements ActionListener {

    private Service service;

    MenuListener(Service service) {
        this.service = service;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if ("startRendering".equals(e.getActionCommand())) {

            Material pyramidSurface = new Material(
                    100,
                    1,
                    0,
                    false,
                    new ColorFilter(0.5f, 0.5f, 0.0f)
            );
            VolumeProperties volumeProperties = new VolumeProperties(new ColorFilter(1,1,1),1.2);
            Triangle[] pyramid = createPyramid(pyramidSurface, volumeProperties, new Vector(0,0,0));

            Material pyramidTransparentSurface = new Material(
                    100,
                    1,
                    0,
                    true,
                    new ColorFilter(1f, 1f, 1f)
            );
            Triangle[] transparentPyramid = createPyramid(pyramidTransparentSurface, volumeProperties, new Vector(0,0,-2));

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

            List<Triangle> triangles = new ArrayList<>();
            triangles.addAll(Arrays.asList(pyramid));
            triangles.addAll(Arrays.asList(transparentPyramid));
            triangles.add(floor);
            triangles.add(mirror);

            List<PointLight> pointLights = Arrays.asList(
                    new PointLight(new Vertex(3, 2, -1), new Color(1, 1, 1)),

                    new PointLight(new Vertex(3, 2, 1), new Color(1, 1, 1))
            );

            Scene scene = new Scene(triangles, pointLights);
            VolumeProperties cameraVolume = new VolumeProperties(new ColorFilter(1,1,1), 1);
            Camera camera = new Camera(
                    new Positioning(
                            new Vector(0, 2, -10), //Camera at the center of the scene
                            UnitVector.construct(0, 0, 1)) //Camera pointing 'forward' into the scene
                    , new Lens(500, 500, 1)
                    , cameraVolume
            );
            service.startRender(scene, camera);
        }
    }

    private Triangle[] createPyramid(Material surface, VolumeProperties volume, Vector position){
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
