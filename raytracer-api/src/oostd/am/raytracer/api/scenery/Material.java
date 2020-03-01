package oostd.am.raytracer.api.scenery;


/**
 * TODO: update documentation
 * material either diffuse or refractive (unless its dirty?)
 * amount of reflection depends on fresnell
 * unless it is a mirror, which reflects a lot at each angle
 *
 * so: everything that is not reflected, might still be reflected based on fresnel calculation
 * e.g. we have 10% reflective (0.1)
 * 50% refractive (0.5) (50% of what is not reflected)
 *  which means that at an angle of 0, 45% is refracted and  45% is diffuse and 10% reflected
 *  but at an angle of 90degrees, 100% of what is not initially reflected is still reflected
 *
 * another thought, depending on the angle more of the color filter is applied to reflective rays? (also depends on the 'hardness' of the material?)
 *
 */
public final class Material {

    public final double shininess;
    public final double diffuseFactor;
    public final double specularFactor;
    public final double reflectionFactor;
    public final ColorFilter colorFilter;
    public final boolean transparent;

    public Material(double shininess, double diffuseFactor, double specularFactor, double reflectionFactor, boolean transparent, ColorFilter colorFilter) {
        this.shininess = shininess;
        this.diffuseFactor = diffuseFactor;
        this.specularFactor = specularFactor; //TODO: make it clear that this only works when material is diffuse.
        this.reflectionFactor = reflectionFactor;
        this.colorFilter = colorFilter;
        this.transparent = transparent;
    }
}
