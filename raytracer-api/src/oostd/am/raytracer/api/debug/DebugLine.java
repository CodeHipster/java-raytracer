package oostd.am.raytracer.api.debug;

import oostd.am.raytracer.api.geography.Vector;

public class DebugLine {
    public Vector from;
    public Vector to;
    public double intensity;

    public DebugLine(Vector from,  Vector to, double intensity){
        this.from = from;
        this.to = to;
        this.intensity = intensity;
    }
}
