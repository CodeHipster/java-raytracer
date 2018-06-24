package oostd.am.raytracer;

import oostd.am.raytracer.api.geography.PixelPosition;
import oostd.am.raytracer.api.geography.UnitVector;
import oostd.am.raytracer.api.geography.Vector;
import oostd.am.raytracer.api.scenery.PointLight;
import oostd.am.raytracer.api.scenery.Triangle;

/**
 * Ray from object pointing towards a lightsource.
 * Always has an InverseRay associated
 */


public class ShadowRay extends Ray{
    public final Triangle triangle;
    public PointLight light;
    public InverseRay inverseRay;
    public ShadowRay(PointLight light, Triangle triangle, Vector position, PixelPosition destination, InverseRay inverseRay) {
        super(UnitVector.construct(light.getPosition().subtract(position)), position, destination);
        this.light = light;
        this.triangle = triangle;
        this.inverseRay = inverseRay;
    }
}