package oostd.am.raytracer.streaming.tracer;

import oostd.am.raytracer.api.geography.UnitVector;
import oostd.am.raytracer.api.geography.Vector;
import oostd.am.raytracer.api.scenery.Triangle;

public class Ray {

    //TODO: remove the need for originTriangle, make sure the Collider won't collide with triangles that are veryClose (double imprecision close)
    public Triangle origin;
    public UnitVector direction;
    public Vector position;

    public Ray(UnitVector direction, Vector position, Triangle origin) {
        this.direction = direction;
        this.position = position;
        this.origin = origin;
    }

    @Override
    public String toString() {
        return "Ray{" +
                "origin=" + origin +
                ", direction=" + direction +
                ", position=" + position +
                '}';
    }
}
