package oostd.am.raytracer.api.geography;

public class Vector2D {
    public double x;

    public Vector2D(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double y;

    @Override
    public String toString() {
        return "Vector2D{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
