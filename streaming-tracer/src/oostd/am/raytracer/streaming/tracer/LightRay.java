package oostd.am.raytracer.streaming.tracer;

import oostd.am.raytracer.api.geography.UnitVector;
import oostd.am.raytracer.api.geography.Vector;
import oostd.am.raytracer.api.scenery.PointLight;
import oostd.am.raytracer.api.scenery.Triangle;

/**
 * Ray from object pointing towards a lightsource.
 * Always has an InverseRay associated, as it adds color to that inverseRay
 */
public class LightRay extends Ray{
    public final Triangle triangle;
    public PointLight light;
    public double intensity;
    public InverseRay inverseRay;
    public LightRay(PointLight light, Triangle triangle, Vector position, InverseRay inverseRay
            , double intensity) {
        super(new UnitVector(light.position.subtract(position)), position);
        this.light = light;
        this.intensity = intensity;
        this.triangle = triangle;
        this.inverseRay = inverseRay;
    }
}
