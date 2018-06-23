package oostd.am.raytracer.api.camera;

import oostd.am.raytracer.api.scenery.ColorFilter;

public class Color {
    public int r,g,b;

    public Color(int r, int g, int b){
        this.r = r;
        this.g = g;
        this.b = b;
    }

    public Color add(Color color){
        this.r += color.r;
        this.g += color.g;
        this.b += color.b;
        return this;
    }

    public Color filter(ColorFilter filter){

        this.r = (int)(r * filter.r);
        this.g = (int)(g * filter.g);
        this.b = (int)(b * filter.b);
        return this;
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
