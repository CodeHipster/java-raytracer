package oostd.am.raytracer.api.debug;

import oostd.am.raytracer.api.geography.Vector2D;

public class Line2D {
    public Vector2D from;
    public Vector2D to;

    public Line2D(Vector2D from, Vector2D to){
        this.from = from;
        this.to = to;
    }

    public void addSelf(Vector2D vector){
        from.addSelf(vector);
        to.addSelf(vector);
    }

    @Override
    public String toString() {
        return "Line2D{" +
                "from=" + from +
                ", to=" + to +
                '}';
    }
}
