package oostd.am.raytracer.api.camera;

public class Camera {
    public Positioning positioning;
    public Lens lens;

    public Camera(Positioning positioning, Lens lens){
        this.positioning = positioning;
        this.lens = lens;
    }
}
