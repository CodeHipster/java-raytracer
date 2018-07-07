package oostd.am.raytracer;

import oostd.am.raytracer.api.geography.Vector;
import oostd.am.raytracer.api.scenery.Triangle;

public class Collision {
    public Triangle target;
    public Vector impactPoint;

    public Collision(Triangle target, Vector impactPoint) {
        this.target = target;
        this.impactPoint = impactPoint;
    }
}
