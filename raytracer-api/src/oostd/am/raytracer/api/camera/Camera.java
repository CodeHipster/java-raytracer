package oostd.am.raytracer.api.camera;

import oostd.am.raytracer.api.scenery.VolumeProperties;

public class Camera {
    public Positioning positioning;
    public Lens lens;
    public VolumeProperties volumeProperties;

    public Camera(Positioning positioning, Lens lens, VolumeProperties volumeProperties){
        this.positioning = positioning;
        this.lens = lens;
        this.volumeProperties = volumeProperties;
    }
}
