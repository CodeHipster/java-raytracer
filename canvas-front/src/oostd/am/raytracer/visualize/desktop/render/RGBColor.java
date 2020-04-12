package oostd.am.raytracer.visualize.desktop.render;

/**
 * Color object. each color component(Red Green Blue) can be a value within 0-255
 */
public class RGBColor {
    public int r, g, b;

    public RGBColor(int rgb){
        r = (rgb & 0x00ff0000) >> 16;
        g = (rgb & 0x0000ff00) >> 8;
        b = (rgb & 0x000000ff);
    }

    /**
     * expects the double to be from 0 to 1 for the visible spectrum. anything higher will be capped.
     */
    public RGBColor add(double r, double g, double b){
        this.r += (int)(r * 255);
        this.g += (int)(g * 255);
        this.b += (int)(b * 255);
        return this;
    }

    /**
     * convert to int where first 2 bytes are untouched, then 2 bytes red, 2 bytes green, 2 bytes blue.
     */
    public int asInt(){
        //put the colors, capped to bytes, in the correct place in the integer.
        return ((Math.min(r, 255)) << 16) + ((Math.min(g, 255)) << 8) + (Math.min(b, 255));
    }

}
