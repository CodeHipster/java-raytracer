package oostd.am.raytracer;

import oostd.am.raytracer.api.geography.PixelPosition;
import oostd.am.raytracer.api.geography.UnitVector;
import oostd.am.raytracer.api.geography.Vector;
import oostd.am.raytracer.api.scenery.VolumeProperties;

public class Ray {

    public UnitVector direction;
    public Vector position;
    public VolumeProperties volumeProperties;

    public Ray(UnitVector direction, Vector position, VolumeProperties volumeProperties) {
        this.direction = direction;
        this.position = position;
        this.volumeProperties = volumeProperties;
    }
}
