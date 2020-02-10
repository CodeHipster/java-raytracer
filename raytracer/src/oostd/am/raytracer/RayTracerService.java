package oostd.am.raytracer;

import oostd.am.raytracer.api.scenery.Scene;

public class RayTracerService implements oostd.am.raytracer.api.RayTracerService {
    @Override
    public void startRendering(Scene scene) {
        new Thread(new Engine(scene)).start();
    }
}
