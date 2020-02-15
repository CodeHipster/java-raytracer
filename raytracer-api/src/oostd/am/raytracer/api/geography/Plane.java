package oostd.am.raytracer.api.geography;

public class Plane {
    public Vector normal;
    public Vector xAxis;
    public Vector origin;

    /**
     *
     * @param origin
     * @param normal
     * @param alignmentVector, aligns the plane's xAxis to this vector
     */
    public Plane(Vector origin, Vector normal, UnitVector alignmentVector) {
        this.origin = origin;
        this.normal = normal;
        this.alignTo(alignmentVector);
    }

    /**
     * Project a 3D point onto a plane
     *   x = (p - O) dot x
     *   y = (p - O) dot (n cross x)
     *   where
     *   p = point to project
     *   O = origin of plane
     *   x = xAxis off the plane in 3d space
     *   n = plane normal
     * @param point
     * @return a 2D vector containing the x, y position on the plane.
     */
    public Vector2D project(Vector point){
        Vector pointToOrigin = point.subtract(origin);
        double x = pointToOrigin.dot(xAxis);
        double y = pointToOrigin.dot(normal.cross(xAxis));
        return new Vector2D(x, y);
    }

    private void alignTo(Vector axis){
        //cross normal with axis
        xAxis = normal.cross(axis).cross(normal);
    }
}
