package oostd.am.raytracer.api.scenery;


/**
 * material either diffuse or refractive (unless its dirty?)
 * amount of reflection depends on fresnell
 * unless it is a mirror, which reflects a lot at each angle
 *
 * so: everything that is not reflected, might still be reflected based on fresnel calculation
 * e.g. we have 10% reflective (0.1)
 * 50% refractive (0.5) (50% of what is not reflected)
 *  which means that at an angle of 0, 45% is refracted and  45% is diffuse and 10% reflected
 *  but at an angle of 90%, 100% of what is not initially reflected is still reflected
 *
 * another thought, depending on the angle more of the color filter is applied to reflective rays? (also depends on the 'hardness' of the material?)
 *
 */
public class Material {
    public final double specularPower;
    public final double specularIntensity;
    public final double reflectance;
    public final double refractance;
    public final ColorFilter colorFilter;

    public Material(double specularPower, double specularIntensity, double reflectance, double refractance, ColorFilter colorFilter) {
        this.specularPower = specularPower;
        this.specularIntensity = specularIntensity;
        this.reflectance = reflectance;
        this.colorFilter = colorFilter;
        this.refractance = refractance;
    }
}
