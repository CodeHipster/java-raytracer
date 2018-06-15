package oostd.am.raytracer.api.geography;

public class UnitVector extends Vector {

    /**
     * Corrects x,y,z so the total length equals 1;
     * @param x
     * @param y
     * @param z
     */
    private UnitVector(double x, double y, double z) {
        super(x, y, z);
    }

    public static UnitVector construct(double x, double y, double z){
        //make length 1.
        double length = Math.sqrt(x*x + y*y + z*z);
        return new UnitVector(x/length, y/length, z/length);
    }
}
