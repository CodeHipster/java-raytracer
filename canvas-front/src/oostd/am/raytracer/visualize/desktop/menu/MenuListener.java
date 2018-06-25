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
import java.util.Arrays;

public class MenuListener implements ActionListener {

    private Service service;

    MenuListener(Service service) {
        this.service = service;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if ("startRendering".equals(e.getActionCommand())) {
            Scene scene = new Scene(
                    Arrays.asList(
                            new Triangle(
                                    new Vertex[]{
                                            new Vertex(-2.0, -1.0, 3.0),
                                            new Vertex(-1.0, 2.0, 4.0),
                                            new Vertex(0.0, -1.0, 5.0)
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
                                            new Vertex(-10.0, -1.0, 0.0),
                                            new Vertex(0.0, -1.0, 20.0),
                                            new Vertex(10.0, -1.0, 0.0)
                                    },
                                    new Material(
                                            100,
                                            1,
                                            0,
                                            0,
                                            new ColorFilter(1.0f, 0.0f, 0.0f)
                                    )
                            ),
                            new Triangle(
                                    new Vertex[]{
                                            new Vertex(-10.0, -1.0, 20.0),
                                            new Vertex(0.0, 10.0, 17.0),
                                            new Vertex(10.0, -1.0, 14.0)
                                    },
                                    new Material(
                                            100,
                                            1,
                                            1,
                                            0,
                                            new ColorFilter(0.0f, 0.0f, 1.0f)
                                    )
                            )
                    ),
                    Arrays.asList(
                            new PointLight(new Vertex(3, 1, 3), new Color(1, 1, 1)),
                            new PointLight(new Vertex(1.1, -0.5, 4.5), new Color(1, 1, 1))
                    )
            );
            Camera camera = new Camera(
                    new Positioning(
                            new Vector(0, 0, 0), //Camera at the center of the scene
                            UnitVector.construct(0, 0, 1)) //Camera pointing 'forward' into the scene
                    , new Lens(500, 500, 1)
            );
            service.startRender(scene, camera);
        }
    }
}
