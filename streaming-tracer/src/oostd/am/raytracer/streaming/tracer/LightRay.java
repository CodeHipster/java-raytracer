package oostd.am.raytracer.streaming.tracer;

import oostd.am.raytracer.api.geography.PixelPosition;
import oostd.am.raytracer.api.geography.UnitVector;
import oostd.am.raytracer.api.geography.Vector;
import oostd.am.raytracer.api.scenery.PointLight;
import oostd.am.raytracer.api.scenery.Triangle;

/**
 * Ray from object pointing towards a lightsource.
 * Always has an InverseRay associated, as it adds color to that inverseRay.
 */
public class LightRay extends Ray{
    public final Triangle triangle;
    public PointLight light;
    public double intensity;
    public PixelPosition pixelPosition;
    public UnitVector viewDirection;
    public LightRay(PointLight light, Triangle triangle, Vector position, PixelPosition pixelPosition
            , double intensity, UnitVector viewDirection) {
        super(new UnitVector(light.position.subtract(position)), position);
        this.light = light;
        this.intensity = intensity;
        this.triangle = triangle;
        this.pixelPosition = pixelPosition;
        this.viewDirection = viewDirection;
    }

    @Override
    public String toString() {
        return "LightRay{" +
                "triangle=" + triangle +
                ", light=" + light +
                ", intensity=" + intensity +
                ", pixelPosition=" + pixelPosition +
                ", viewDirection=" + viewDirection +
                ", direction=" + direction +
                ", position=" + position +
                '}';
    }
}
