package oostd.am.raytracer;

import oostd.am.raytracer.api.geography.UnitVector;
import oostd.am.raytracer.api.geography.Vector;
import oostd.am.raytracer.api.scenery.PointLight;
import oostd.am.raytracer.api.scenery.Triangle;
import oostd.am.raytracer.api.scenery.VolumeProperties;

/**
 * Ray from object pointing towards a lightsource.
 * Always has an InverseRay associated, as it adds color to that inverseRay
 */
public class ShadowRay extends Ray{
    public final Triangle triangle;
    public PointLight light;
    public InverseRay inverseRay;
    public ShadowRay(PointLight light, Triangle triangle, Vector position, InverseRay inverseRay
            , VolumeProperties volumeProperties) {
        super(UnitVector.construct(light.position.subtractNew(position)), position, volumeProperties);
        this.light = light;
        this.triangle = triangle;
        this.inverseRay = inverseRay;
    }
}
