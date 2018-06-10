package oostd.am.raytracer.visualize.desktop.menu;

import oostd.am.raytracer.api.camera.Camera;
import oostd.am.raytracer.api.camera.Lens;
import oostd.am.raytracer.api.camera.Positioning;
import oostd.am.raytracer.api.scenery.Scenery;
import oostd.am.raytracer.visualize.desktop.Service;
import oostd.am.raytracer.visualize.desktop.render.RenderPixelSubscriber;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuListener implements ActionListener {

    private Service service;

    public MenuListener(Service service){
        this.service = service;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if ("startRendering".equals(e.getActionCommand())) {
            service.startRender(new Scenery(), new Camera(new Positioning(1,1,1,1), new Lens(300,300, 1)));
        }
    }
}
