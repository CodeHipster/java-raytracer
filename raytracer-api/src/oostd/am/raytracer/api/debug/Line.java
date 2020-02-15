package oostd.am.raytracer.api.debug;

import oostd.am.raytracer.api.geography.Vector;

public class Line {
    public Vector from;
    public Vector to;
    public double intensity; //TODO: are we doing something with this?

    public Line(Vector from, Vector to, double intensity){
        this.from = from;
        this.to = to;
        this.intensity = intensity;
    }
}
