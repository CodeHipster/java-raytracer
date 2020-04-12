package oostd.am.raytracer.streaming.tracer;

import oostd.am.raytracer.api.geography.Vector;
import oostd.am.raytracer.api.scenery.SceneObject;
import oostd.am.raytracer.api.scenery.Triangle;

/**
 * Object resembling the intersection of a ray with an object from the scene.
 * @param <R>
 */
public class Collision<R extends Ray> {
    public R ray;
    public SceneObject target;
    public Vector impactPoint;

    public Collision(SceneObject target, Vector impactPoint, R ray) {
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
