package oostd.am.raytracer.api.camera;

public class Color {
    public int r,g,b;

    public Color(int r, int g, int b){
        this.r = r;
        this.g = g;
        this.b = b;
    }

    @Override
    public String toString() {
        return "Color{" +
                "r=" + r +
                ", g=" + g +
                ", b=" + b +
                '}';
    }
}
