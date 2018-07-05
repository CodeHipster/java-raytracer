package oostd.am.raytracer;

import oostd.am.raytracer.api.geography.PixelPosition;
import oostd.am.raytracer.api.geography.UnitVector;
import oostd.am.raytracer.api.geography.Vector;
import oostd.am.raytracer.api.scenery.Material;

public class RefractionRay extends Ray {

    //The material a refraction ray is inside of.
    public Material material;
    public RefractionRay(UnitVector direction, Vector position, PixelPosition destination, double intensity) {
        super(direction, position, destination, intensity);
    }

}
