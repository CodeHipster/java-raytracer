package oostd.am.raytracer.api.camera;

import oostd.am.raytracer.api.debug.Window;
import oostd.am.raytracer.api.geography.Dimension;
import oostd.am.raytracer.api.geography.UnitVector;
import oostd.am.raytracer.api.geography.Vector;

/**
 * Camera that looks into a scene and outputs what it sees through a Flow.Subscriber
 */
//TODO: make lens dimensions configurable?
    // how will we handle aspect ratio?

public class Camera {
    public Window lens;
    public Vector position;
    public double lensOffset;

    public Camera(Vector position, UnitVector direction, UnitVector alignTo, double lensOffset){
        this(position, direction, alignTo, lensOffset, null);
    }

    public Camera(Vector position, UnitVector direction, UnitVector alignTo, double lensOffset, String name){
        this.position = position;
        Vector camToLens = direction.scale(lensOffset);
        Vector windowOrigin = position.add(camToLens);
        this.lens = new Window(windowOrigin, direction, alignTo, new Dimension(1,1), name);
        this.lensOffset = lensOffset;
    }
}
