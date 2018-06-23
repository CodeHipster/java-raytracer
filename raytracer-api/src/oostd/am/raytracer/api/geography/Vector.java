package oostd.am.raytracer.api.geography;

public class Vector {

    public final double x,y,z;
    public Vector(double x, double y,double z){
        this.x = x;
        this.y = y;
        this.z = z;
    }

    /**
     * Calculate the dot product of this vector with other vector (T.O)
     * Returns a scalar
     */
    public double dot(Vector other) {
        return this.x*other.x + this.y * other.y + this.z * other.z;
    }

    /**
     * Subtract other vector from this one.
     * returns a new vector.
     */
    public Vector subtract(Vector other){
        return new Vector(this.x - other.x, this.y - other.y, this.z - other.z);
    }

    public Vector multiply(double scalar){
        return new Vector(x *scalar, y * scalar, z *scalar);
    }

    public Vector add(Vector other) {
        return new Vector(x + other.x, y + other.y, z + other.z);
    }

    /**
     * Calculate cross product of this vector and other (TxO)
     * returns a new vector.
     */
    public Vector cross(Vector other){
        return new Vector(
                this.y * other.z - this.z * other.y,
                this.z * other.x - this.x * other.z,
                this.x * other.y - this.y * other.x);
    }

    public double length(){
        return Math.sqrt(x*x + y*y + z*z);
    }

}
