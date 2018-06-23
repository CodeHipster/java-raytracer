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
            service.startRender(
                    new Scene(
                            Arrays.asList(
                                    new Triangle(new Vertex[]{
                                            new Vertex(-1.0, -1.0, 3.0),
                                            new Vertex(0.0, 2.0, 3.0),
                                            new Vertex(1.0, -1.0, 3.0)
                                    },
                                            new ColorFilter(0.5f,0.5f,0.0f))),
                            Arrays.asList(new PointLight(new Vertex(0, 2, 1.5), new Color(1,1,1)))),
                    new Camera(
                            new Positioning(
                                    new Vector(0, 0, 0), //Camera at the center of the scene
                                    UnitVector.construct(0, 0, 1)) //Camera pointing 'forward' into the scene
                            , new Lens(300, 300, 1)));
        }
    }
}
