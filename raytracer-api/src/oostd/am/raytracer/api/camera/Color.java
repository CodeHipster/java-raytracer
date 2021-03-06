package oostd.am.raytracer.api.camera;

public class Color {
    public static Color WHITE = new Color(1,1,1);
    public static Color RED = new Color(1,0,0);
    public static Color GREEN = new Color(0,1,0);
    public static Color BLUE = new Color(0,0,1);

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
        return new Color(this.r + color.r, this.g + color.g, this.b + color.b);
    }

    public Color scale(double factor){
        return new Color(r * factor, g * factor, b * factor);
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
