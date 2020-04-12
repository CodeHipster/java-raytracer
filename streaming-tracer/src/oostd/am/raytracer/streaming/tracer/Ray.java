package oostd.am.raytracer.streaming.tracer;

import oostd.am.raytracer.api.geography.UnitVector;
import oostd.am.raytracer.api.geography.Vector;
import oostd.am.raytracer.api.scenery.Triangle;

/**
 * Basic definition of a ray
 */
public class Ray {

    public UnitVector direction;
    public Vector position;

    public Ray(UnitVector direction, Vector position) {
        this.direction = direction;
        this.position = position;
    }

    @Override
    public String toString() {
        return "Ray{" +
                "direction=" + direction +
                ", position=" + position +
                '}';
    }
}
