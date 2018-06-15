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
}

//TODO: vector subtraction
// dot product
// cross product
