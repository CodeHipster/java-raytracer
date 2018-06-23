package oostd.am.raytracer;

import oostd.am.raytracer.api.geography.PixelPosition;
import oostd.am.raytracer.api.geography.UnitVector;
import oostd.am.raytracer.api.geography.Vector;

public class Ray {

    protected UnitVector direction;
    protected Vector position;
    protected PixelPosition destination;

    public Ray(UnitVector direction, Vector position, PixelPosition destination) {
        this.direction = direction;
        this.position = position;
        this.destination = destination;
    }

    public UnitVector getDirection() {
        return direction;
    }

    public Vector getPosition() {
        return position;
    }

    public PixelPosition getDestination() {
        return destination;
    }
}
