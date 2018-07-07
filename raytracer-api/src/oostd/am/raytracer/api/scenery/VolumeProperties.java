package oostd.am.raytracer.api.scenery;

public class VolumeProperties {
    public final ColorFilter colorFilter;
    public final double refractionIndex;

    public VolumeProperties(ColorFilter colorFilter, double refractionIndex) {
        this.colorFilter = colorFilter;
        this.refractionIndex = refractionIndex;
    }
}
