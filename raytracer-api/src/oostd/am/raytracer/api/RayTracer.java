package oostd.am.raytracer.api;

import oostd.am.raytracer.api.camera.Camera;
import oostd.am.raytracer.api.camera.Pixel;
import oostd.am.raytracer.api.scenery.Scenery;

import java.util.ArrayList;
import java.util.List;
import java.util.ServiceLoader;
import java.util.stream.Stream;

public interface RayTracer {
    static List<RayTracer> getInstances() {
        ServiceLoader<RayTracer> services = ServiceLoader.load(RayTracer.class);
        List<RayTracer> list = new ArrayList<>();
        services.iterator().forEachRemaining(list::add);
        return list;
    }

    Stream<Pixel> render(Scenery scenery, Camera camera);
}
