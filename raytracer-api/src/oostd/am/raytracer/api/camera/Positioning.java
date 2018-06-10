package oostd.am.raytracer.api.camera;

/**
 * Positioning defined as a quaternion
 */
public class Positioning {
    public double a,b,c,d;

    /**
     * 4 values of a quaternion defining the position and orientation of the camera.
     * @param a
     * @param b
     * @param c
     * @param d
     */
    public Positioning(double a, double  b, double  c, double  d){
        this.a = a;
        this.b = b;
        this.c = c;
        this.d = d;
    }
}
