package oostd.am.raytracer.visualize.desktop;

import oostd.am.raytracer.api.PixelSubscriberFactory;
import oostd.am.raytracer.api.RayTracerService;
import oostd.am.raytracer.api.scenery.Scene;

import java.util.List;

public class RenderService {

    private RayTracerService rayTracerService;
    private PixelSubscriberFactory pixelSubscriberFactory;

    public RenderService(PixelSubscriberFactory pixelSubscriberFactory){
        this.pixelSubscriberFactory = pixelSubscriberFactory;
        List<RayTracerService> rayTracerServices = RayTracerService.getInstances();
        if(rayTracerServices.isEmpty()) throw new RuntimeException("No raytracer service found.");
        if(rayTracerServices.size() > 1){
            System.out.println("Found multiple instances of the raytracer service. Using the first one.");
            for(RayTracerService service : rayTracerServices){
                System.out.println(service.toString());
            }
        }
        rayTracerService = rayTracerServices.get(0);
    }

    public void startRender(Scene scene){
        rayTracerService.startRendering(scene, pixelSubscriberFactory);
        System.out.println("started timer.");
    }
}
