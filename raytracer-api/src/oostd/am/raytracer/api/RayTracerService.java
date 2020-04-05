package oostd.am.raytracer.api;

import oostd.am.raytracer.api.scenery.Scene;

import java.util.ArrayList;
import java.util.List;
import java.util.ServiceLoader;

public interface RayTracerService {

    /**
     * This method should immediately return and render asynchronously
     * @param scene
     */
    void startRendering(Scene scene, PixelSubscriberFactory pixelSubscriberFactory);
}
