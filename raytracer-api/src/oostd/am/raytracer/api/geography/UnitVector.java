package oostd.am.raytracer.api.geography;

public class UnitVector extends Vector{

    /**
     * Corrects x,y,z so the total length equals 1;
     *
     * @param x
     * @param y
     * @param z
     */
    public UnitVector(double x, double y, double z) {
        super(x,y,z);
        double length = Math.sqrt(x * x + y * y + z * z);
        double inverseLength = 1 / length;
        this.x *= inverseLength;
        this.y *= inverseLength;
        this.z *= inverseLength;
    }

    public UnitVector(Vector vector) {
        this(vector.x, vector.y, vector.z);
    }

    @Override
    public UnitVector invertSelf() {
        super.invertSelf();
        return this;
    }

    @Override
    public UnitVector invert() {
        return new UnitVector(super.invert());
    }

    public UnitVector cross(UnitVector other){
        return new UnitVector(super.cross(other));
    }

    public UnitVector reflectOn(UnitVector surfaceNormal) {
        Vector reflection = this.subtract(surfaceNormal.scale(this.dot(surfaceNormal) * 2));
        return new UnitVector(reflection.x, reflection.y, reflection.z);
    }

    @Override
    public Vector addSelf(Vector other) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Vector scaleSelf(double scalar) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Vector subtractSelf(Vector other) {
        throw new UnsupportedOperationException();
    }
}
