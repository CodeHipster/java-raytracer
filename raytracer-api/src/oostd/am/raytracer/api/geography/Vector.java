package oostd.am.raytracer.api.geography;


/**
 * x = right
 * y = up
 * z = forward
 */
public class Vector {

    public double x, y, z;

    public Vector(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    /**
     * Calculate the dot product of this vector with other vector (T.O)
     * Returns a scalar
     */
    public double dot(Vector other) {
        return this.x * other.x + this.y * other.y + this.z * other.z;
    }

    /**
     * returns a new vector.
     * where other vector is subtracted from this one.
     */
    public Vector subtractNew(Vector other) {
        return new Vector(this.x - other.x, this.y - other.y, this.z - other.z);
    }

    /**
     * Subtract other vector from this one.
     *
     * @param other
     */
    public void subtract(Vector other) {
        this.x -= other.x;
        this.y -= other.y;
        this.z -= other.z;
    }

    public Vector scaleNew(double scalar) {
        return new Vector(x * scalar, y * scalar, z * scalar);
    }

    public void scale(double scalar) {
        x *= scalar;
        y *= scalar;
        z *= scalar;
    }

    public Vector addNew(Vector other) {
        return new Vector(x + other.x, y + other.y, z + other.z);
    }

    public Vector addSelf(Vector other) {
        x += other.x;
        y += other.y;
        z += other.z;
        return this;
    }

    /**
     * Calculate cross product of this vector and other (TxO)
     * returns a new vector.
     */
    public Vector cross(Vector other) {
        return new Vector(
                this.y * other.z - this.z * other.y,
                this.z * other.x - this.x * other.z,
                this.x * other.y - this.y * other.x);
    }

    public double length() {
        return Math.sqrt(x * x + y * y + z * z);
    }

    public double square() {
        return x * x + y * y + z * z;
    }
}
