package oostd.am.raytracer.api.geography;

public class Plane {
    public UnitVector normal;
    public UnitVector xAxis;
    public UnitVector yAxis;
    public Vector origin;

    /**
     * create a plane and align it vertically with the given alignTo vector.
     * //TODO: determine what to do if normal and align vector are equal.
     */
    public Plane(Vector origin, UnitVector normal, UnitVector alignTo) {
        this.origin = origin;
        this.normal = normal;
        this.xAxis = this.normal.cross(alignTo);
        this.yAxis = this.xAxis.cross(normal);
        this.xAxis.invertSelf();
    }

    /**
     * Project a 3D point onto a plane
     * x = (p - O) dot x
     * y = (p - O) dot (n cross x)
     * where
     * p = point to project
     * O = origin of plane
     * x = xAxis off the plane in 3d space
     * n = plane normal
     *
     * @param point
     * @return a 2D vector containing the x, y position on the plane.
     */
    public Vector2D project(Vector point) {
        Vector pointToOrigin = point.subtract(origin);
        double x = pointToOrigin.dot(xAxis);
        double y = pointToOrigin.dot(yAxis);
        return new Vector2D(x, y);
    }

    public Vector positionOf(Vector2D vector2D) {
        Vector x = xAxis.scale(vector2D.x);
        Vector y = yAxis.scale(vector2D.y);
        return x.addSelf(y).addSelf(origin);
    }
}
