package oostd.am.raytracer.random;

import oostd.am.raytracer.api.PixelSubscriberFactory;
import oostd.am.raytracer.api.debug.Window;
import oostd.am.raytracer.api.scenery.Scene;

/**
 * raytracer implementation that does not trace rays, but instead returns random colors for random pixels.
 */
public class RandomPixelService implements oostd.am.raytracer.api.RayTracerService {
    @Override
    public void startRendering(Scene scene, PixelSubscriberFactory pixelSubscriberFactory) {
        Thread pixelThread = new Thread(
                new PixelSupplier(pixelSubscriberFactory.createRenderSubscriber("random pixels")));
        pixelThread.start();

        for(Window window: scene.debugWindows){
            Thread thread = new Thread(
                    new PixelSupplier(
                            pixelSubscriberFactory.createDebugSubscriber("random pixels")));
            thread.start();
        }

    }
}
