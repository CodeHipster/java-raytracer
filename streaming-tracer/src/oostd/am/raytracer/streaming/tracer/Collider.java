package oostd.am.raytracer.streaming.tracer;

import oostd.am.raytracer.api.geography.Vector;
import oostd.am.raytracer.api.scenery.Sphere;
import oostd.am.raytracer.api.scenery.Triangle;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

/**
 * ThreadSafe
 */
public class Collider {

    private static final double EPSILON = Math.ulp(1000);
    private final List<Triangle> triangles;
    private final List<Sphere> spheres;

    public Collider(List<Triangle> triangles, List<Sphere> spheres) {
        this.triangles = triangles;
        this.spheres = spheres;
    }

    public <R extends Ray> Collision<R> collide(final R ray) {
        Optional<Intersection<Triangle>> closestTriangle = triangles.stream()
                .map(triangle -> new Intersection<>(triangle, CollisionCalculator.calculateCollisionDistance(triangle, ray)))
                .filter(intersection -> intersection.distance > EPSILON)
                .min(Comparator.comparingDouble(o -> o.distance));
        Optional<Intersection<Sphere>> closestSphere = spheres.stream()
                .map(sphere -> new Intersection<>(sphere, CollisionCalculator.calculateCollisionDistance(sphere, ray)))
                .filter(intersection -> intersection.distance > EPSILON)
                .min(Comparator.comparingDouble(o -> o.distance));
        if (closestTriangle.isPresent() && closestSphere.isPresent()) {
            if (closestTriangle.get().distance < closestSphere.get().distance) {
                Intersection<Triangle> intersection = closestTriangle.get();
                Vector collisionPoint = ray.position.add(ray.direction.scale(intersection.distance));
                return new Collision<>(intersection.target, collisionPoint, ray);
            } else {
                Intersection<Sphere> intersection = closestSphere.get();
                Vector collisionPoint = ray.position.add(ray.direction.scale(intersection.distance));
                return new Collision<>(intersection.target, collisionPoint, ray);
            }
            //TODO: refactor the entire sphere thing...
        } else if (closestTriangle.isPresent()) {
            Intersection<Triangle> intersection = closestTriangle.get();
            Vector collisionPoint = ray.position.add(ray.direction.scale(intersection.distance));
            return new Collision<>(intersection.target, collisionPoint, ray);
        } else if (closestSphere.isPresent()){
            Intersection<Sphere> intersection = closestSphere.get();
            Vector collisionPoint = ray.position.add(ray.direction.scale(intersection.distance));
            return new Collision<>(intersection.target, collisionPoint, ray);
        }else{
            return null;
        }
    }

    private static class Intersection<T> {
        public final T target;
        public final double distance;

        Intersection(T target, double distance) {
            this.target = target;
            this.distance = distance;
        }
    }
}
