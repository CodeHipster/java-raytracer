package oostd.am.raytracer;

import oostd.am.raytracer.api.geography.Vector;
import oostd.am.raytracer.api.scenery.Triangle;

public class CollisionCalculator {

    /**
     * returns a Vector with point of collision, or null if nothing collided.
     * @param triangle
     * @param ray
     * @return
     */
    public static Vector calculateCollision(Triangle triangle, Ray ray){
//        static float rayTriangleIntersect(Ray r, Vec3 v0, Vec3 v1, Vec3 v2) {
//            Vec3 v0v1 = v1.sub(v0);
//            Vec3 v0v2 = v2.sub(v0);
//            Vec3 pvec = r.dir.cross(v0v2);
//            float det = v0v1.dot(pvec);
//
//            if (det < 0.000001)
//                return NEGATIVE_INFINITY;
//
//            float invDet = (float) (1.0 / det);
//            Vec3 tvec = r.orig.sub(v0);
//            float u = tvec.dot(pvec) * invDet;
//
//            if (u < 0 || u > 1)
//                return NEGATIVE_INFINITY;
//
//            Vec3 qvec = tvec.cross(v0v1);
//            float v = r.dir.dot(qvec) * invDet;
//
//            if (v < 0 || u + v > 1)
//                return NEGATIVE_INFINITY;
//
//            return (float) (v0v2.dot(qvec) * invDet);
//        }
        return null;
    }
}

//TODO: vector subtraction
// dot product
// cross product
