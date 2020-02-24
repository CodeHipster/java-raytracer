package oostd.am.raytracer.api.debug;

import oostd.am.raytracer.api.geography.Dimension;
import oostd.am.raytracer.api.geography.Plane;
import oostd.am.raytracer.api.geography.UnitVector;
import oostd.am.raytracer.api.geography.Vector;

public class Window extends Plane{
    public Dimension dimension;
    public String name;

    public Window(Vector origin, UnitVector normal, UnitVector alignTo, Dimension dimension) {
        super(origin, normal, alignTo);
        this.dimension = dimension;
    }

    public Window(Vector origin, UnitVector normal, UnitVector alignTo, Dimension dimension, String name) {
        this(origin, normal, alignTo, dimension);
        this.name = name;
    }
}
