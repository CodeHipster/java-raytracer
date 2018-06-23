package oostd.am.raytracer.api.scenery;

/**
 * filter determines how much light is absorbed and how much is reflected.
 * 1 means all is reflected, 0 means all is absorbed.
 */
public class ColorFilter {
    public float r,g,b;

    public ColorFilter(float r, float g, float b){
        this.r = r;
        this.g = g;
        this.b = b;
    }
}
