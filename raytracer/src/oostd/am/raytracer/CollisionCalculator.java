package oostd.am.raytracer;

import oostd.am.raytracer.api.geography.Vector;
import oostd.am.raytracer.api.scenery.Triangle;

public class CollisionCalculator {

    private static double EPSILON = Math.ulp(1.0);

    /**
     * Algorithm from Moller, Trumbore, "Fast, Minimum Storage
     *  Ray / Triangle Intersection", Journal of Graphics Tools, Volume 2,
     *  Number 1, 1997, pp. 21-28.
     */
    public static double calculateCollisionDistance(Triangle triangle, Ray ray) {
        Vector vert0 = triangle.vertices[0];

        Vector edge1 = triangle.vertices[1].subtract(triangle.vertices[0]);
        Vector edge2 = triangle.vertices[2].subtract(triangle.vertices[0]);

        Vector n = edge1.cross(edge2);
        double norm = Math.sqrt(n.square());
        if(norm == 0)
            return -1;

        // Begin calculating determinant -- also used to calculate U parameter
        if(ray == null || ray.direction == null || edge2 == null){
            int de = 1;
        }
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
