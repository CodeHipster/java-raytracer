package oostd.am.raytracer.api.geography;

public class PixelPosition {
    public int x,y;

    public PixelPosition(int x, int y){
        this.x = x;
        this.y = y;
    }

    @Override
    public String toString() {
        return "PixelPosition{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
