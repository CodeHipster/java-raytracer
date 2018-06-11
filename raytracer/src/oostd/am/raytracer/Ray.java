package oostd.am.raytracer;

import oostd.am.raytracer.api.camera.Quaternion;

public class Ray {
    private Quaternion positioning;

    public Ray(Quaternion positioning){
        this.positioning = positioning;
    }
}
