package oostd.am.raytracer.api.debug;

import oostd.am.raytracer.api.geography.Dimension;
import oostd.am.raytracer.api.geography.Plane;
import oostd.am.raytracer.api.geography.UnitVector;
import oostd.am.raytracer.api.geography.Vector;

public class Window extends Plane{
    public Dimension dimension;
    public double left, right, top, bottom;

    public Window(Vector origin, Vector normal, UnitVector xAxis, Dimension dimension) {
        super(origin, normal, xAxis);
        this.dimension = dimension;
        this.right = dimension.width / 2;
        this.left = this.right * -1;
        this.top = dimension.height / 2;
        this.bottom = this.top * -1;
    }

    /**
     * cross axis with normal to get a perpendicular axis in world space
     * cross that again to get an axis in the window space
     * //TODO: test and see what happens if axis is parallel with normal.
     */
    private void alignTo(Vector axis){
        //cross normal with axis
        this.xAxis = normal.cross(axis).cross(normal);
    }
}
