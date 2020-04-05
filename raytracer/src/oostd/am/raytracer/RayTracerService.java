package oostd.am.raytracer;

import oostd.am.raytracer.api.PixelSubscriberFactory;
import oostd.am.raytracer.api.scenery.Scene;
import oostd.am.raytracer.engine.Engine;

public class RayTracerService implements oostd.am.raytracer.api.RayTracerService {

    private Thread thread;

    @Override
    public void startRendering(Scene scene, PixelSubscriberFactory pixelSubscriberFactory) {
        if (thread != null) {
            System.out.println("rendering already started, not starting again.");
        } else {
            thread = new Thread(new Engine(scene, pixelSubscriberFactory));
            thread.start();
        }
    }
}
