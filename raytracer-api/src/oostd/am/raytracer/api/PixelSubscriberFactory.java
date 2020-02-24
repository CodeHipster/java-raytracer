package oostd.am.raytracer.api;

import oostd.am.raytracer.api.camera.PixelSubscriber;

public interface PixelSubscriberFactory {
    PixelSubscriber createRenderSubscriber(String name);
    PixelSubscriber createDebugSubscriber(String name);
}
