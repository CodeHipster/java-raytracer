package oostd.am.raytracer;

import oostd.am.raytracer.api.geography.PixelPosition;
import oostd.am.raytracer.api.geography.UnitVector;
import oostd.am.raytracer.api.geography.Vector;
import oostd.am.raytracer.api.scenery.Triangle;

/**
 * an inverse ray is a ray from a destination (camera, reflective surface e.g.)
 * It is called inverse as it traces in the opposite direction a photon would go.
 */
public class InverseRay extends Ray{
    public final int depth;
    public Triangle origin;
    public final double refractionIndex;

    public InverseRay(int depth, double intensity, double refractionIndex, UnitVector direction, Vector position, PixelPosition destination, Triangle origin) {
        super(direction, position, destination, intensity);
        this.refractionIndex = refractionIndex;
        this.depth = depth;
        this.origin = origin;
    }
}
