package oostd.am.raytracer.streaming.tracer;

import oostd.am.raytracer.api.geography.PixelPosition;
import oostd.am.raytracer.api.geography.UnitVector;
import oostd.am.raytracer.api.geography.Vector;
import oostd.am.raytracer.api.scenery.Triangle;

/**
 * an inverse ray is a ray from a destination (camera, reflective surface e.g.)
 * It is called inverse as it traces in the opposite direction a photon would go.
 */
public class InverseRay extends Ray{
    public final int depth; //nr of reflections/refractions
    //TODO: refactor this out, offset collision point by epsilon.
    public Triangle origin; //the surface it comes from (reflected or refracted, or null from camera.)
    public PixelPosition pixelPosition; // the position on the lens it has effect on.
    public final double intensity; // the amount of effect on the lens (in terms of color intensity from 0 - 1).

    public InverseRay(int depth, double intensity, UnitVector direction, Vector position
            , PixelPosition pixelPosition, Triangle origin) {
        super(direction, position, origin);
        this.depth = depth;
        this.origin = origin;
        this.intensity = intensity;
        this.pixelPosition = pixelPosition;
    }

    @Override
    public String toString() {
        return "InverseRay{" +
                "depth=" + depth +
                ", origin=" + origin +
                ", pixelPosition=" + pixelPosition +
                ", intensity=" + intensity +
                '}';
    }
}
