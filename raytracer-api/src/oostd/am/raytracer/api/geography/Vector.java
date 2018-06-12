package oostd.am.raytracer.api.geography;

public class Vector {

    protected double x,y,z;
    public Vector(double x, double y,double z){
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getZ() {
        return z;
    }
}
