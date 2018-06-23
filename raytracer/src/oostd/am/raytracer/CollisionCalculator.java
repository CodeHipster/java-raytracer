package oostd.am.raytracer;

import oostd.am.raytracer.api.geography.Vector;
import oostd.am.raytracer.api.scenery.Triangle;

public class CollisionCalculator {


    private static double EPSILON = Math.ulp(1.0);
    /**
     * returns a Vector with point of collision, or null if nothing collided.
     * @param triangle
     * @param ray
     * @return
     */
    public static Vector calculateCollision(Triangle triangle, Ray ray){
        Vector v0v1 = triangle.vertices[1].subtract(triangle.vertices[0]);
        Vector v0v2 = triangle.vertices[2].subtract(triangle.vertices[0]);

        Vector perpendicular = ray.direction.cross(v0v2);
        double determinant = v0v1.dot(perpendicular);

        if(determinant < EPSILON){
            return null;//return Double.NEGATIVE_INFINITY;
        }

        double inverseDeterminant = 1.0 / determinant;
        Vector tVec = ray.position.subtract(triangle.vertices[0]);

        double u = tVec.dot(perpendicular) * inverseDeterminant;

        if( u < 0 || u > 1) return null;

        Vector qVec = tVec.cross(v0v1);
        double v = ray.direction.dot(qVec) * inverseDeterminant;

        if( u < 0 || u + v > 1) return null;

        double distance = v0v2.dot(qVec) * inverseDeterminant;

        return ray.direction.multiply(distance).add(ray.position);
    }

    /**
     * Algorithm from Moller, Trumbore, "Fast, Minimum Storage
     *  Ray / Triangle Intersection", Journal of Graphics Tools, Volume 2,
     *  Number 1, 1997, pp. 21-28.
     */
    public static Vector calculateCollision2(Triangle triangle, Ray ray) {
        final double SMALL_NUM = 0.000001f;
        Vector vert0 = triangle.vertices[0];

        Vector edge1 = triangle.vertices[1].subtract(triangle.vertices[0]);
        Vector edge2 = triangle.vertices[2].subtract(triangle.vertices[0]);

        Vector n = edge1.cross(edge2);
        double norm = Math.sqrt(n.x*n.x + n.y*n.y + n.z*n.z);
        if(norm == 0)
            return null;

        // Begin calculating determinant -- also used to calculate U parameter
        Vector pvec = ray.direction.cross(edge2);

        // If determinant is near zero, ray lies in plane of triangle
        float det = (float) edge1.dot(pvec);

        if (det > -SMALL_NUM && det < SMALL_NUM)
            return null;

        float invDet = 1.0f / det;

        // Calculate distance from vert0 to ray origin
        Vector tvec = ray.position.subtract(vert0);
        float a = (float) -(n.dot(tvec));
        float b = (float) n.dot(ray.direction);

        if(a / b < 0)
            return null;

        // Calculate U parameter and test bounds
        float u = (float) (tvec.dot(pvec) * invDet);
        if (u < 0.0f || u > 1.0f)
            return null;

        // Prepare to test V parameter
        Vector qvec = tvec.cross(edge1);

        // Calculate V parameter and test bounds
        float v = (float) (ray.direction.dot(qvec) * invDet);
        if (v < 0.0f || (u + v) > 1.0f)
            return null;

        // Calculate t, ray intersects triangle
        float t = (float) (edge2.dot(qvec) * invDet);


        //is this correct?
        return ray.direction.multiply(t);
    }

    public static double calculateCollisionDistance(Triangle triangle, LightRay ray) {
        Vector vector = CollisionCalculator.calculateCollision2(triangle, ray);
        if(vector == null) return Double.POSITIVE_INFINITY;
        //TODO optimize away the sqrt
        return Math.sqrt(vector.x * vector.x + vector.y * vector.y + vector.z * vector.z);
    }
}

//TODO: vector subtraction
// dot product
// cross product
