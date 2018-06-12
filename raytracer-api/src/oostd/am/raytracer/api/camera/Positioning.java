package oostd.am.raytracer.api.camera;

import oostd.am.raytracer.api.geography.UnitVector;
import oostd.am.raytracer.api.geography.Vector;

/**
 * Positioning defined as a quaternion
 */
public class Positioning {
    public Vector position;
    public UnitVector direction;

    public Positioning(Vector position, UnitVector direction){
        this.position = position;
        this.direction = direction;
    }
}
