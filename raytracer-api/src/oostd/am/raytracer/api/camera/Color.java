package oostd.am.raytracer.api.camera;

import oostd.am.raytracer.api.scenery.ColorFilter;

public class Color {
    public double r,g,b;

    public Color(double r, double g, double b){
        this.r = r;
        this.g = g;
        this.b = b;
    }

    public Color clone(){
        return new Color(r,g,b);
    }

    public Color add(Color color){
        this.r += color.r;
        this.g += color.g;
        this.b += color.b;
        return this;
    }

    public Color scale(double factor){
        this.r = r * factor;
        this.g = g * factor;
        this.b = b * factor;
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
