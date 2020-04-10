package oostd.am.raytracer.api.scenery;

import oostd.am.raytracer.api.geography.Vector;

public class Sphere extends SceneObject {
    public Vector positon;
    public double radius;

    public Sphere(Vector positon, double radius, Material material, VolumeProperties volumeProperties) {
        super(material, volumeProperties);
        this.positon = positon;
        this.radius = radius;
    }
}
