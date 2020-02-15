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

    public static UnitVector construct(Vector vector){
        return UnitVector.construct(vector.x, vector.y, vector.z);
    }

    public UnitVector inverse() {
        return new UnitVector(x * -1.0, y * -1.0, z * -1.0);
    }

    public UnitVector reflectOn(UnitVector surfaceNormal){

        UnitVector inverseLightNormal = this.inverse();
        Vector reflection = surfaceNormal.scaleNew(inverseLightNormal.dot(surfaceNormal) * 2).addNew(this);
        return new UnitVector(reflection.x, reflection.y, reflection.z);
    }
}
