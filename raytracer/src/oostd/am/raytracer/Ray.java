package oostd.am.raytracer;

import oostd.am.raytracer.api.geography.UnitVector;
import oostd.am.raytracer.api.geography.Vector;

public class Ray {

    protected UnitVector direction;
    protected Vector position;

    public Ray(UnitVector direction, Vector position) {
        this.direction = direction;
        this.position = position;
    }

    public UnitVector getDirection() {
        return direction;
    }

    public Vector getPosition() {
        return position;
    }
}
