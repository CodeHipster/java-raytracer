package oostd.am.raytracer.visualize.desktop;

import oostd.am.raytracer.api.PixelSubscriberFactory;
import oostd.am.raytracer.api.RayTracerService;
import oostd.am.raytracer.api.scenery.Scene;

import java.util.ArrayList;
import java.util.List;
import java.util.ServiceLoader;

/**
 * Service for starting the ray-tracer.
 * Uses the first ray-tracer implementation it can find.
 */
public class RenderService {

    private final RayTracerService rayTracerService;
    private final PixelSubscriberFactory pixelSubscriberFactory;

    public RenderService(PixelSubscriberFactory pixelSubscriberFactory){
        this.pixelSubscriberFactory = pixelSubscriberFactory;
        List<RayTracerService> rayTracerServices = this.getInstances();
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
    }

    List<RayTracerService> getInstances() {
        ServiceLoader<RayTracerService> services = ServiceLoader.load(RayTracerService.class);
        List<RayTracerService> list = new ArrayList<>();
        services.iterator().forEachRemaining(list::add);
        return list;
    }
}
