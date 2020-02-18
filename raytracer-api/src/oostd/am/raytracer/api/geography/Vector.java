package oostd.am.raytracer.api.geography;


import java.util.Objects;

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
    public Vector subtract(Vector other) {
        return new Vector(this.x - other.x, this.y - other.y, this.z - other.z);
    }

    /**
     * Subtract other vector from this one.
     *
     * @param other
     */
    public Vector subtractSelf(Vector other) {
        this.x -= other.x;
        this.y -= other.y;
        this.z -= other.z;
        return this;
    }

    public Vector scale(double scalar) {
        return new Vector(x * scalar, y * scalar, z * scalar);
    }

    public Vector scaleSelf(double scalar) {
        x *= scalar;
        y *= scalar;
        z *= scalar;
        return this;
    }

    public Vector add(Vector other) {
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

    public Vector invertSelf() {
        x *= -1.0;
        y *= -1.0;
        z *= -1.0;
        return this;
    }

    public Vector invert() {
        return new Vector(x * -1.0, y * -1.0, z * -1.0);
    }

    public UnitVector unit(){
        return new UnitVector(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vector vector = (Vector) o;
        return Double.compare(vector.x, x) == 0 &&
                Double.compare(vector.y, y) == 0 &&
                Double.compare(vector.z, z) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y, z);
    }
}
