package oostd.am.raytracer.api.camera;

/**
 * Camera that looks into a scene and outputs what it sees through a Flow.Subscriber
 */
public class Camera {
    public Positioning positioning;
    public int lensOffset;

    //TODO: doesnt this thing needs an xAxis?
    public Camera(Positioning positioning, int lensOffset){
        this.positioning = positioning;
        this.lensOffset = lensOffset;
    }
}
