package oostd.am.raytracer.collision;

import oostd.am.raytracer.Collision;
import oostd.am.raytracer.CollisionCalculator;
import oostd.am.raytracer.Ray;
import oostd.am.raytracer.api.geography.Vector;
import oostd.am.raytracer.api.scenery.Triangle;

import java.util.List;

public class CollisionService {

    private static double EPSILON = Math.ulp(1.0);

    private List<Triangle> triangles;

    public CollisionService(List<Triangle> triangles) {
        this.triangles = triangles;
    }
    public Collision findCollision(Ray ray, Triangle origin) {
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

    /**
     * Algorithm from Moller, Trumbore, "Fast, Minimum Storage
     *  Ray / Triangle Intersection", Journal of Graphics Tools, Volume 2,
     *  Number 1, 1997, pp. 21-28.
     */
    public double calculateCollisionDistance(Triangle triangle, Ray ray) {
        Vector vert0 = triangle.vertices[0];

        Vector edge1 = triangle.vertices[1].subtract(triangle.vertices[0]);
        Vector edge2 = triangle.vertices[2].subtract(triangle.vertices[0]);

        Vector n = edge1.cross(edge2);
        double norm = Math.sqrt(n.square());
        if(norm == 0)
            return -1;

        // Begin calculating determinant -- also used to calculate U parameter
        Vector pvec = ray.direction.cross(edge2);

        // If determinant is near zero, ray lies in plane of triangle
        double det = edge1.dot(pvec);

        if (det > -EPSILON && det < EPSILON)
            return -1;

        double invDet = 1.0 / det;

        // Calculate distance from vert0 to ray origin
        Vector tvec = ray.position.subtract(vert0);
        double a = -(n.dot(tvec));
        double b = n.dot(ray.direction);

        if(a / b < 0)
            return -1;

        // Calculate U parameter and test bounds
        double u = tvec.dot(pvec) * invDet;
        if (u < 0.0 || u > 1.0)
            return -1;

        // Prepare to test V parameter
        Vector qvec = tvec.cross(edge1);

        // Calculate V parameter and test bounds
        double v = ray.direction.dot(qvec) * invDet;
        if (v < 0.0 || (u + v) > 1.0)
            return -1;

        // Calculate t, ray intersects triangle
        return edge2.dot(qvec) * invDet;
    }
}
