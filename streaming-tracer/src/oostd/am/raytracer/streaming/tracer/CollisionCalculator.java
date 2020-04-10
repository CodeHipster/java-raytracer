package oostd.am.raytracer.streaming.tracer;

import oostd.am.raytracer.api.geography.Vector;
import oostd.am.raytracer.api.scenery.Sphere;
import oostd.am.raytracer.api.scenery.Triangle;

public class CollisionCalculator {

    private static double EPSILON = Math.ulp(1.0);

    /**
     * Algorithm from Moller, Trumbore, "Fast, Minimum Storage
     * Ray / Triangle Intersection", Journal of Graphics Tools, Volume 2,
     * Number 1, 1997, pp. 21-28.
     */
    public static double calculateCollisionDistance(Triangle triangle, Ray ray) {
        Vector vert0 = triangle.vertices[0];

        Vector edge1 = triangle.vertices[1].subtract(triangle.vertices[0]);
        Vector edge2 = triangle.vertices[2].subtract(triangle.vertices[0]);

        //We can take pre calculated normal from triangle.
        Vector n = edge1.cross(edge2);
        double norm = Math.sqrt(n.square());
        if (norm == 0)
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

        if (a / b < 0)
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

    //https://www.scratchapixel.com/lessons/3d-basic-rendering/minimal-ray-tracer-rendering-simple-shapes/ray-sphere-intersection
    public static double calculateCollisionDistance(Sphere sphere, Ray ray) {
        Vector L = sphere.positon.subtract(ray.position);
        double Tca = ray.direction.dot(L);
        double radius = sphere.radius;
        if (Tca < radius * -1) { // Ray starts outside sphere and points in the wrong direction.
            return -1;
        }

        double lengthL = L.length();
        double TcaSquared = Tca * Tca;
        double lengthLSquared = lengthL * lengthL;
        double dSquared = lengthLSquared - TcaSquared;
        double radiusSquared = radius * radius;
        if (dSquared > radiusSquared) {
            return -1; // There is no intersection with the sphere
        }
        double ThcSquared = radiusSquared - dSquared;

        if (Tca < 0) {
            return Math.sqrt(ThcSquared) - Math.sqrt(TcaSquared);
        } else if (Tca <= radius) {
            return Math.sqrt(TcaSquared) + Math.sqrt(ThcSquared);
        } else {
            return Math.sqrt(TcaSquared) - Math.sqrt(ThcSquared);
        }
    }
}
