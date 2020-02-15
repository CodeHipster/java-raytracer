package oostd.am.raytracer;

import oostd.am.raytracer.api.PixelSubscriberFactory;
import oostd.am.raytracer.api.scenery.Scene;

public class RayTracerService implements oostd.am.raytracer.api.RayTracerService {

    @Override
    public void startRendering(Scene scene, PixelSubscriberFactory pixelSubscriberFactory) {
        //TODO: do not start another thread if this one is still running.
        new Thread(new Engine(scene, pixelSubscriberFactory)).start();
    }
}
