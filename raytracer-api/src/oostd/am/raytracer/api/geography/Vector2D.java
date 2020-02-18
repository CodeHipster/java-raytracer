package oostd.am.raytracer.api.geography;

import java.util.Objects;

public class Vector2D {
    public double x;
    public double y;

    public Vector2D(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public void addSelf(Vector2D other) {
        this.x += other.x;
        this.y += other.y;
    }

    public void scaleSelf(double xs, double ys) {
        this.x *= xs;
        this.y *= ys;
    }

    public Vector2D subtract(Vector2D other) {
        double x = this.x - other.x;
        double y = this.y - other.y;
        return new Vector2D(x, y);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vector2D vector2D = (Vector2D) o;
        return Double.compare(vector2D.x, x) == 0 &&
                Double.compare(vector2D.y, y) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    @Override
    public String toString() {
        return "Vector2D{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
