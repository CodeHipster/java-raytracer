package oostd.am.raytracer.streaming.tracer;

import oostd.am.raytracer.api.geography.Vector;
import oostd.am.raytracer.api.scenery.Triangle;

public class Collision<R extends Ray> {
    public R ray;
    public Triangle target;
    public Vector impactPoint;

    public Collision(Triangle target, Vector impactPoint, R ray) {
        this.target = target;
        this.impactPoint = impactPoint;
        this.ray = ray;
    }

    @Override
    public String toString() {
        return "Collision{" +
                "ray=" + ray +
                ", target=" + target +
                ", impactPoint=" + impactPoint +
                '}';
    }
}
