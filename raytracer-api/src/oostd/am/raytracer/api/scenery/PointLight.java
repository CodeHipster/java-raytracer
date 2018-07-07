package oostd.am.raytracer.api.scenery;

import oostd.am.raytracer.api.camera.Color;

public class PointLight {
    public Vertex position;
    public Color color;

    public PointLight(Vertex position, Color color){
        this.position = position;
        this.color = color;
    }
}
