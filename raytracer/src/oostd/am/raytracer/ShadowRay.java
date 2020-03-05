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
    public double intensity;
    public InverseRay inverseRay;
    public ShadowRay(PointLight light, Triangle triangle, Vector position, InverseRay inverseRay
            , VolumeProperties volumeProperties, double intensity) {
        super(new UnitVector(light.position.subtract(position)), position, volumeProperties);
        this.light = light;
        this.intensity = intensity;
        this.triangle = triangle;
        this.inverseRay = inverseRay;
    }
}
