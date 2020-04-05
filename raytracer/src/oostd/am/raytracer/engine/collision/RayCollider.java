package oostd.am.raytracer.engine.collision;

import oostd.am.raytracer.engine.Collision;
import oostd.am.raytracer.engine.CollisionCalculator;
import oostd.am.raytracer.engine.Ray;
import oostd.am.raytracer.api.geography.Vector;
import oostd.am.raytracer.api.scenery.Triangle;

import java.util.List;

public class RayCollider {

    public static Collision findCollision(Ray ray, Triangle origin, List<Triangle> triangles) {
        // check collision with triangles;
        double distance = Double.POSITIVE_INFINITY;
        Triangle target = null;

        for (Triangle triangle : triangles) {
            if (triangle == origin) continue; //skip triangle we come from.
            double d = CollisionCalculator.calculateCollisionDistance(triangle, ray);
            if (d > 0 && d < distance) {
                distance = d;
                target = triangle;
            }
        }
        if (target == null) return null; // did not hit anything.
        Vector collisionPoint = ray.position.add(ray.direction.scale(distance));
        return new Collision(target, collisionPoint);
    }

}
