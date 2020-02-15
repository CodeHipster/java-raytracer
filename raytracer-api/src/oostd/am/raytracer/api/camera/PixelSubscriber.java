package oostd.am.raytracer.api.camera;

import java.util.concurrent.Flow;

public interface PixelSubscriber extends Flow.Subscriber<Pixel> {
    Resolution getResolution();
}
