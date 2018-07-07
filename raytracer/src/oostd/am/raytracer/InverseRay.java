package oostd.am.raytracer;

import oostd.am.raytracer.api.geography.PixelPosition;
import oostd.am.raytracer.api.geography.UnitVector;
import oostd.am.raytracer.api.geography.Vector;
import oostd.am.raytracer.api.scenery.Triangle;
import oostd.am.raytracer.api.scenery.VolumeProperties;

/**
 * an inverse ray is a ray from a destination (camera, reflective surface e.g.)
 * It is called inverse as it traces in the opposite direction a photon would go.
 */
public class InverseRay extends Ray{
    public final int depth; //nr of reflections/refractions
    public Triangle origin; //the surface it comes from (reflected or refracted, or null from camera.) //TODO: refactor this out, offset collision point by epsilon.
    public PixelPosition pixelPosition; // the position on the lens it has effect on.
    public final double intensity; // the amount of effect on the lens (in terms of color intensity from 0 - 1).

    public InverseRay(int depth, double intensity, UnitVector direction, Vector position
            , PixelPosition pixelPosition, Triangle origin, VolumeProperties volumeProperties) {
        super(direction, position, volumeProperties);
        this.depth = depth;
        this.origin = origin;
        this.intensity = intensity;
        this.pixelPosition = pixelPosition;
    }
}
