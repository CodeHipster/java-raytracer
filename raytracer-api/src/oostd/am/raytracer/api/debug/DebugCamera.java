package oostd.am.raytracer.api.debug;

import oostd.am.raytracer.api.camera.Pixel;
import oostd.am.raytracer.api.camera.Positioning;
import oostd.am.raytracer.api.camera.Resolution;

import java.util.concurrent.Flow;

/**
 * a debug camera looks orthogonally into the scene (not from a single point like a normal camera)
 */
public class DebugCamera {
    /**
     * position of the cam in the scene
     */
    public Positioning positioning;
    /**
     * width of the 'window' of the camera
     */
    public int width;
    /**
     * height of the 'window' of the camera
     */
    public int height;
    /**
     * resolution of the camera, the amount of pixels that the 'window' is made of.
     */
    public Resolution resolution;

    /**
     * object that will consume the output of the camera.
     */
    public Flow.Subscriber<Pixel> outputConsumer;

    public DebugCamera(Positioning positioning, int width, int height, Resolution resolution, Flow.Subscriber<Pixel> outputConsumer) {
        this.positioning = positioning;
        this.width = width;
        this.height = height;
        this.resolution = resolution;
        this.outputConsumer = outputConsumer;
    }
}
