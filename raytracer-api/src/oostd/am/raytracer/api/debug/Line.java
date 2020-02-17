package oostd.am.raytracer.api.debug;

import oostd.am.raytracer.api.camera.Color;
import oostd.am.raytracer.api.geography.Vector;

//TODO: color instead of intensity
public class Line {
    public Vector from;
    public Vector to;
    public Color color;

    public Line(Vector from, Vector to, Color color){
        this.from = from;
        this.to = to;
        this.color = color;
    }
}
