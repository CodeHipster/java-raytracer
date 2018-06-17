package oostd.am.raytracer.visualize.desktop;

import oostd.am.raytracer.api.RayTracerService;
import oostd.am.raytracer.api.camera.Camera;
import oostd.am.raytracer.api.scenery.Scene;
import oostd.am.raytracer.visualize.desktop.render.RenderFrame;
import oostd.am.raytracer.visualize.desktop.render.RenderPanel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class Service {

    private RayTracerService rayTracerService;
    public final static int INTERVAL = 1000;

    public Service(){
        List<RayTracerService> instances = RayTracerService.getInstances();
        if(instances.isEmpty()) throw new RuntimeException("No raytracer service found.");
        rayTracerService = instances.get(0);
    }
    public void startRender(Scene scene, Camera camera){
        RenderPanel renderPanel = new RenderPanel();
        SwingUtilities.invokeLater(() -> {
            System.out.println("Created renderFrame on EDT? "+ SwingUtilities.isEventDispatchThread());
            RenderFrame renderFrame = new RenderFrame(camera.lens.width, camera.lens.height);
            renderFrame.add(renderPanel);
        });
        rayTracerService.startRendering(renderPanel, scene, camera);

        System.out.println("creating timer.");
        Timer timer = new Timer(INTERVAL, evt -> {
            //Refresh the panel
            renderPanel.repaint();
            System.out.println("repainting renderPanel.");

//            if (/* condition to terminate the thread. */) {
//                timer.stop();
//            }
        });

        timer.start();

        System.out.println("started timer.");
    }
}
