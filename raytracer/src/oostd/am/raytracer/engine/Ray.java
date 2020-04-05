package oostd.am.raytracer.engine;

import oostd.am.raytracer.api.geography.UnitVector;
import oostd.am.raytracer.api.geography.Vector;

public class Ray {

    public UnitVector direction;
    public Vector position;

    public Ray(UnitVector direction, Vector position) {
        this.direction = direction;
        this.position = position;
    }
}
