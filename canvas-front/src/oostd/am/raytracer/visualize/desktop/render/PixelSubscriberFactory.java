package oostd.am.raytracer.visualize.desktop.render;

import oostd.am.raytracer.api.camera.Pixel;
import oostd.am.raytracer.api.camera.Resolution;

import java.util.concurrent.Flow;

public interface PixelSubscriberFactory {
    Flow.Subscriber<Pixel> createSubscriber(Resolution resolution);
}
