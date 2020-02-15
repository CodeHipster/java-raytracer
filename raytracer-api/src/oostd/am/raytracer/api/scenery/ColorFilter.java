package oostd.am.raytracer.api.scenery;

import oostd.am.raytracer.api.camera.Color;

/**
 * filter determines how much light is absorbed and how much is reflected.
 * 1 means all is reflected, 0 means all is absorbed.
 */
public final class ColorFilter {
    public float r,g,b;

    public ColorFilter(float r, float g, float b){
        this.r = r;
        this.g = g;
        this.b = b;
    }

    public Color filter(Color color){
        color.r *= this.r;
        color.g *= this.g;
        color.b *= this.b;
        return color;
    }
}
