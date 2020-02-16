package oostd.am.raytracer.api.debug;

import oostd.am.raytracer.api.geography.Vector;

//TODO: color instead of intensity
public class Line {
    public Vector from;
    public Vector to;
    public double intensity;

    public Line(Vector from, Vector to, double intensity){
        this.from = from;
        this.to = to;
        this.intensity = intensity;
    }
}
