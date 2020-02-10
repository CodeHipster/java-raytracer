package oostd.am.raytracer.visualize.desktop;

import oostd.am.raytracer.api.RayTracerService;
import oostd.am.raytracer.api.scenery.Scene;

import java.util.List;

public class RenderService {

    private RayTracerService rayTracerService;
    public final static int INTERVAL = 33;

    public RenderService(){
        List<RayTracerService> instances = RayTracerService.getInstances();
        if(instances.isEmpty()) throw new RuntimeException("No raytracer service found.");
        if(instances.size() > 1){
            System.out.println("Found multiple instances of the raytracer service. Using the first one.");
            for(RayTracerService service : instances){
                System.out.println(service.toString());
            }
        }
        rayTracerService = instances.get(0);
    }

    //TODO: service need no knowledge of screens etc.
    public void startRender(Scene scene){
        rayTracerService.startRendering(scene);

//        System.out.println("creating timer.");
//        Timer timer = new Timer(INTERVAL, evt -> {
//            //Refresh the panel
//            renderPanel.repaint();
//
////            if (/* condition to terminate the thread. */) {
////                timer.stop();
////            }
//        });

//        timer.start();

        System.out.println("started timer.");
    }
}
