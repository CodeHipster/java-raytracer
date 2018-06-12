package oostd.am.raytracer.api.geography;

public class UnitVector extends Vector {

    /**
     * Corrects x,y,z so the total length equals 1;
     * @param x
     * @param y
     * @param z
     */
    public UnitVector(double x, double y, double z) {
        super(x, y, z);

        //make length 1.
        double length = Math.sqrt(x*x + y*y + z*z);
        this.x /= length;
        this.y /= length;
        this.z /= length;
    }
}
