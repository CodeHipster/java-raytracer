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

            Triangle[] pyramid = new Triangle[]{
                    new Triangle(
                            new Vertex[]{
                                    new Vertex(-1.0, 0.0, -1.0),
                                    new Vertex(0.0, 1.0, 0.0),
                                    new Vertex(1.0, 0.0, -1.0)
                            },
                            new Material(
                                    100,
                                    1,
                                    0,
                                    0,
                                    new ColorFilter(0.5f, 0.5f, 0.0f)
                            )
                    ),
                    new Triangle(
                            new Vertex[]{
                                    new Vertex(1.0, 0.0, -1.0),
                                    new Vertex(0.0, 1.0, 0.0),
                                    new Vertex(1.0, 0.0, 1.0)
                            },
                            new Material(
                                    100,
                                    1,
                                    0,
                                    0,
                                    new ColorFilter(0.5f, 0.5f, 0.0f)
                            )
                    ),
                    new Triangle(
                            new Vertex[]{
                                    new Vertex(1.0, 0.0, 1.0),
                                    new Vertex(0.0, 1.0, 0.0),
                                    new Vertex(-1.0, 0.0, 1.0)
                            },
                            new Material(
                                    100,
                                    1,
                                    0,
                                    0,
                                    new ColorFilter(0.5f, 0.5f, 0.0f)
                            )
                    ),
                    new Triangle(
                            new Vertex[]{
                                    new Vertex(-1.0, 0.0, 1.0),
                                    new Vertex(0.0, 1.0, 0.0),
                                    new Vertex(-1.0, 0.0, -1.0)
                            },
                            new Material(
                                    100,
                                    1,
                                    0,
                                    0,
                                    new ColorFilter(0.5f, 0.5f, 0.0f)
                            )
                    )
            };
            Triangle floor = new Triangle(
                    new Vertex[]{
                            new Vertex(-100.0, 0.0, -100.0),
                            new Vertex(0.0, 0.0, 200.0),
                            new Vertex(100.0, 0.0, -100.0)
                    },
                    new Material(
                            1000,
                            1,
                            0,
                            0,
                            new ColorFilter(1.0f, 0.0f, 0.0f)
                    )
            );
            Triangle mirror = new Triangle(
                    new Vertex[]{
                            new Vertex(-10.0, 0.0, 20.0),
                            new Vertex(0.0, 10.0, 20.0),
                            new Vertex(10.0, 0.0, 20.0)
                    },
                    new Material(
                            1000000,
                            0,
                            1,
                            0,
                            new ColorFilter(0.0f, 0.0f, 1.0f)
                    )
            );

            List<Triangle> triangles = new ArrayList<>();
            //triangles.addAll(Arrays.asList(pyramid));
            triangles.add(floor);
            triangles.add(mirror);

            List<PointLight> pointLights = Arrays.asList(
                    new PointLight(new Vertex(2, 1.2, 0), new Color(1, 1, 1))
            );

            Scene scene = new Scene(triangles, pointLights);
            Camera camera = new Camera(
                    new Positioning(
                            new Vector(0, 2, -10), //Camera at the center of the scene
                            UnitVector.construct(0, 0, 1)) //Camera pointing 'forward' into the scene
                    , new Lens(500, 500, 1)
            );
            service.startRender(scene, camera);
        }
    }
}
