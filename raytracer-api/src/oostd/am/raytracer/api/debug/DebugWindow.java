package oostd.am.raytracer.api.debug;

import oostd.am.raytracer.api.geography.Dimension;
import oostd.am.raytracer.api.geography.Plane;
import oostd.am.raytracer.api.geography.UnitVector;
import oostd.am.raytracer.api.geography.Vector;

/**
 * DebugWindow wraps a DebugCamera to translate from lines to pixels.
 */
public class DebugWindow extends Plane {

    public Dimension dimension;
    public double left, right, top, bottom;

    public DebugWindow(Vector origin, Vector normal, UnitVector alignmentVector, Dimension dimension) {
        super(origin, normal, alignmentVector);
        this.dimension = dimension;
        this.right = dimension.width / 2;
        this.left = this.right * -1;
        this.top = dimension.height / 2;
        this.bottom = this.top * -1;
    }
}
