package oostd.am.raytracer;

import oostd.am.raytracer.api.geography.PixelPosition;
import oostd.am.raytracer.api.geography.UnitVector;
import oostd.am.raytracer.api.geography.Vector;

public class Ray {

    public UnitVector direction;
    public Vector position;
    public PixelPosition destination;
    //for what factor the ray adds to the final color
    public final double intensity;

    public Ray(UnitVector direction, Vector position, PixelPosition destination, double intensity) {
        this.direction = direction;
        this.position = position;
        this.destination = destination;
        this.intensity = intensity;
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
