package oostd.am.raytracer.api.geography;

import java.util.Objects;

/**
 * Position of a pixel on the 2d output
 */
public class PixelPosition {
    public int x,y;

    public PixelPosition(int x, int y){
        this.x = x;
        this.y = y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PixelPosition that = (PixelPosition) o;
        return x == that.x &&
                y == that.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    @Override
    public String toString() {
        return "PixelPosition{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
