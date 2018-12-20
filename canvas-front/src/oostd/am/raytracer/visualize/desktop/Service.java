package oostd.am.raytracer.visualize.desktop;

import oostd.am.raytracer.api.RayTracerService;
import oostd.am.raytracer.api.camera.Camera;
import oostd.am.raytracer.api.geography.UnitVector;
import oostd.am.raytracer.api.scenery.Scene;
import oostd.am.raytracer.visualize.desktop.render.DebugFrame;
import oostd.am.raytracer.visualize.desktop.render.RightDebugPanel;
import oostd.am.raytracer.visualize.desktop.render.RenderFrame;
import oostd.am.raytracer.visualize.desktop.render.RenderPanel;

import javax.swing.*;
import java.util.List;

public class Service {

    private RayTracerService rayTracerService;
    public final static int INTERVAL = 33;

    public Service(){
        List<RayTracerService> instances = RayTracerService.getInstances();
        if(instances.isEmpty()) throw new RuntimeException("No raytracer service found.");
        for(RayTracerService service : instances){
            if(service.getClass().toGenericString().equals("public class oostd.am.raytracer.random.RayTracerService")){
                rayTracerService = service;
            }

        }
    }
    public void startRender(Scene scene, Camera camera){
        RenderPanel renderPanel = new RenderPanel(camera.lens.width, camera.lens.height);
        RightDebugPanel rightDebugPanel = new RightDebugPanel(UnitVector.construct(-1,0,0));
        SwingUtilities.invokeLater(() -> {
            System.out.println("Created renderFrame on EDT? "+ SwingUtilities.isEventDispatchThread());
            RenderFrame renderFrame = new RenderFrame(camera.lens.width, camera.lens.height);
            renderFrame.add(renderPanel);
            DebugFrame debugFrame = new DebugFrame();
            debugFrame.add(rightDebugPanel);
        });
        rayTracerService.startRendering(renderPanel, rightDebugPanel, scene, camera);

        System.out.println("creating timer.");
        Timer timer = new Timer(INTERVAL, evt -> {
            //Refresh the panel
            renderPanel.repaint();

//            if (/* condition to terminate the thread. */) {
//                timer.stop();
//            }
        });

        timer.start();

        System.out.println("started timer.");
    }
}
