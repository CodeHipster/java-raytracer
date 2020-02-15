package oostd.am.raytracer.api.scenery;

import oostd.am.raytracer.api.RayTracerService;

import java.util.ArrayList;
import java.util.List;
import java.util.ServiceLoader;

public interface SceneService {
    static List<SceneService> getInstances() {
        ServiceLoader<SceneService> services = ServiceLoader.load(SceneService.class);
        List<SceneService> list = new ArrayList<>();
        services.iterator().forEachRemaining(list::add);
        return list;
    }

    Scene getScene();
}
