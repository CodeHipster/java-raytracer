package oostd.am.raytracer.api;

import oostd.am.raytracer.api.camera.Camera;
import oostd.am.raytracer.api.camera.Pixel;
import oostd.am.raytracer.api.scenery.Scene;

import java.util.ArrayList;
import java.util.List;
import java.util.ServiceLoader;
import java.util.concurrent.Flow;

public interface RayTracerService {
    static List<RayTracerService> getInstances() {
        ServiceLoader<RayTracerService> services = ServiceLoader.load(RayTracerService.class);
        List<RayTracerService> list = new ArrayList<>();
        services.iterator().forEachRemaining(list::add);
        return list;
    }

    void startRendering(Flow.Subscriber<Pixel> subscriber, Scene scene, Camera camera);
}
