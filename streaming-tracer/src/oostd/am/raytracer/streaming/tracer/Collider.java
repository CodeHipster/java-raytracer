package oostd.am.raytracer.streaming.tracer;

import oostd.am.raytracer.api.geography.Vector;
import oostd.am.raytracer.api.scenery.Triangle;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

/**
 * ThreadSafe
 */
public class Collider {

    private static final double EPSILON = Math.ulp(1000);
    private final List<Triangle> sceneGeometry;

    public Collider(List<Triangle> sceneGeometry) {
        this.sceneGeometry = sceneGeometry;
    }

    public <R extends Ray> Collision<R> collide(final R ray) {
        Optional<Intersection> closest = sceneGeometry.stream()
                .map(triangle -> new Intersection(triangle, CollisionCalculator.calculateCollisionDistance(triangle, ray)))
                .filter(intersection -> intersection.distance > EPSILON)
                .min(Comparator.comparingDouble(o -> o.distance));
        if (closest.isPresent()) {
            Intersection intersection = closest.get();
            Vector collisionPoint = ray.position.add(ray.direction.scale(intersection.distance));
            return new Collision<>(intersection.target, collisionPoint, ray);
        } else {
            return null;
        }
    }

    private static class Intersection {
        public final Triangle target;
        public final double distance;

        Intersection(Triangle target, double distance) {
            this.target = target;
            this.distance = distance;
        }
    }
}
