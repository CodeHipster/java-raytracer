package oostd.am.raytracer.api.scenery;


public abstract class SceneObject {
    public Material material;
    public VolumeProperties volumeProperties;

    public SceneObject(Material material, VolumeProperties volumeProperties) {
        this.material = material;
        this.volumeProperties = volumeProperties;
    }
}
