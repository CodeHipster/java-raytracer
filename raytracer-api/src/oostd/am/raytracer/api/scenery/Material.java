package oostd.am.raytracer.api.scenery;


/**
 */
public class Material {
    public final double specularPower;
    public final double specularIntensity;
    public final double reflectionFactor;
    public final ColorFilter colorFilter;

    public Material(double specularPower, double specularIntensity, double reflectionFactor, ColorFilter colorFilter) {
        this.specularPower = specularPower;
        this.specularIntensity = specularIntensity;
        this.reflectionFactor = reflectionFactor;
        this.colorFilter = colorFilter;
    }
}
