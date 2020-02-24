package oostd.am.raytracer.api.camera;

import java.util.concurrent.Flow;

/**
 * PixelSubscriber handles a flow of pixels.
 * Each input for the same pixel position is an addition to what has already been drawn. (not a replacement)
 */
public interface PixelSubscriber extends Flow.Subscriber<Pixel> {
    Resolution getResolution();
    String getName();
}
