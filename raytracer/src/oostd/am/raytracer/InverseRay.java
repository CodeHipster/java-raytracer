package oostd.am.raytracer;

import oostd.am.raytracer.api.camera.Pixel;
import oostd.am.raytracer.api.geography.PixelPosition;
import oostd.am.raytracer.api.geography.UnitVector;
import oostd.am.raytracer.api.geography.Vector;

/**
 * an inverse ray is a ray from a destination (camera, reflective surface e.g.)
 * It is called inverse as it traces in the opposite direction a photon would go.
 */
public class InverseRay extends Ray{
    private int depth;
    private PixelPosition destination;

    public InverseRay(int depth, UnitVector direction, Vector position, PixelPosition destination) {
        super(direction, position);
        this.depth = depth;
        this.destination = destination;
    }

    public int getDepth() {
        return depth;
    }

    public PixelPosition getDestination() {
        return destination;
    }
}
