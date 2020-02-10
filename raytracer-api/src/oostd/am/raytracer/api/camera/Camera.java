package oostd.am.raytracer.api.camera;

import java.util.concurrent.Flow;

/**
 * Camera that looks into a scene and outputs what it sees through a Flow.Subscriber
 */
public class Camera {
    public Positioning positioning;
    public int lensOffset;
    public Resolution resolution;
    public Flow.Subscriber<Pixel> outputConsumer;

    public Camera(Positioning positioning, int lensOffset, Resolution resolution, Flow.Subscriber<Pixel> outputConsumer){
        this.positioning = positioning;
        this.lensOffset = lensOffset;
        this.resolution = resolution;
        this.outputConsumer = outputConsumer;
    }
}
