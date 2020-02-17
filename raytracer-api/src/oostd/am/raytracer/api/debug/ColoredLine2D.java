package oostd.am.raytracer.api.debug;

import oostd.am.raytracer.api.camera.Color;
import oostd.am.raytracer.api.geography.Vector2D;

public class ColoredLine2D extends Line2D {

    public Color color;

    public ColoredLine2D(Vector2D from, Vector2D to, Color color) {
        super(from, to);
        this.color = color;
    }
}
