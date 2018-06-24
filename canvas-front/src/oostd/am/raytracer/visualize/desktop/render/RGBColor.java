package oostd.am.raytracer.visualize.desktop.render;

public class RGBColor {
    private int r, g, b;

    public RGBColor(int rgb){
        r = (rgb & 0x00ff0000) >> 16;
        g = (rgb & 0x0000ff00) >> 8;
        b = (rgb & 0x000000ff);
    }

    public RGBColor(int r, int g, int b){
        this.r = r;
        this.g = g;
        this.b = b;
    }

    /**
     * expects the double to be from 0 to 1 for the visible spectrum. anything higher will be capped
     * @param r
     * @param g
     * @param b
     * @return
     */
    public RGBColor add(double r, double g, double b){
        this.r += (int)(r * 255);
        this.g += (int)(g * 255);
        this.b += (int)(b * 255);
        return this;
    }

    /**
     * convert to int where first 2 bytes are untouched, then 2 bytes red, 2 bytes green, 2 bytes blue
     * @return
     */
    public int asInt(){
        //put the colors, capped to bytes, in the correct place in the integer.
        return (((r > 255)? 255 : r) << 16) + (((g > 255)? 255 : g) << 8) + ((b > 255)? 255 : b);
    }

}
