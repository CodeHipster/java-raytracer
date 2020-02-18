package oostd.am.raytracer.api.debug;

import oostd.am.raytracer.api.geography.Dimension;
import oostd.am.raytracer.api.geography.Plane;
import oostd.am.raytracer.api.geography.UnitVector;
import oostd.am.raytracer.api.geography.Vector;

public class Window extends Plane{
    public Dimension dimension;
    //TODO: these don't belong here, move them to where they are used. Seems specific to LineClipper
    public double left, right, top, bottom;

    public Window(Vector origin, UnitVector normal, UnitVector alignTo, Dimension dimension) {
        super(origin, normal, alignTo);
        this.dimension = dimension;
        this.right = dimension.width / 2;
        this.left = this.right * -1;
        this.top = dimension.height / 2;
        this.bottom = this.top * -1;
    }
}
