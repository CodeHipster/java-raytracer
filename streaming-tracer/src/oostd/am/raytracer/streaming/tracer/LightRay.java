package oostd.am.raytracer.streaming.tracer;

import oostd.am.raytracer.api.geography.PixelPosition;
import oostd.am.raytracer.api.geography.UnitVector;
import oostd.am.raytracer.api.geography.Vector;
import oostd.am.raytracer.api.scenery.PointLight;
import oostd.am.raytracer.api.scenery.SceneObject;

/**
 * Ray from object pointing towards a lightsource.
 * Always has an InverseRay associated, as it adds color to that inverseRay.
 */
public class LightRay extends Ray{
    public final SceneObject sceneObject;
    public PointLight light;
    public double intensity;
    public PixelPosition pixelPosition;
    public UnitVector viewDirection;
    public UnitVector surfaceNormal;

    public LightRay(PointLight light, SceneObject sceneObject, Vector position, PixelPosition pixelPosition
            , double intensity, UnitVector viewDirection, UnitVector surfaceNormal) {
        super(new UnitVector(light.position.subtract(position)), position);
        this.light = light;
        this.intensity = intensity;
        this.sceneObject = sceneObject;
        this.pixelPosition = pixelPosition;
        this.viewDirection = viewDirection;
        this.surfaceNormal = surfaceNormal;
    }

    @Override
    public String toString() {
        return "LightRay{" +
                "sceneObject=" + sceneObject +
                ", light=" + light +
                ", intensity=" + intensity +
                ", pixelPosition=" + pixelPosition +
                ", viewDirection=" + viewDirection +
                ", direction=" + direction +
                ", position=" + position +
                '}';
    }
}
