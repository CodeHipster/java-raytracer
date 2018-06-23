package oostd.am.raytracer;

import oostd.am.raytracer.api.geography.PixelPosition;
import oostd.am.raytracer.api.geography.UnitVector;
import oostd.am.raytracer.api.geography.Vector;
import oostd.am.raytracer.api.scenery.PointLight;
import oostd.am.raytracer.api.scenery.Triangle;

//Ray from object pointing towards a lightsource.
public class LightRay extends Ray{
    public final Triangle triangle;
    public PointLight light;
    public InverseRay predecessor;
    public LightRay(PointLight light, Triangle triangle, UnitVector direction, Vector position, PixelPosition destination, InverseRay predecessor) {
        super(direction, position, destination);
        this.light = light;
        this.triangle = triangle;
        this.predecessor = predecessor;
    }
}
