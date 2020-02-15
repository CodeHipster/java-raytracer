package oostd.am.raytracer.api.scenery;

import oostd.am.raytracer.api.camera.Color;
import oostd.am.raytracer.api.geography.Vector;

public final class PointLight {
    public Vector position;
    public Color color;

    public PointLight(Vector position, Color color){
        this.position = position;
        this.color = color;
    }
}
