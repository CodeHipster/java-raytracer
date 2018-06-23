package oostd.am.raytracer.api.scenery;

import oostd.am.raytracer.api.camera.Color;
import oostd.am.raytracer.api.geography.UnitVector;
import oostd.am.raytracer.api.geography.Vector;

public class Triangle {
    public Vertex[] vertices;
    public ColorFilter colorFilter;
    public UnitVector surfaceNormal;

    public Triangle(Vertex[] vertices, ColorFilter colorFilter){
        if(vertices.length != 3) throw new IllegalArgumentException("exactly 3 vertices required in a triangle.");
        this.vertices = vertices;
        this.colorFilter = colorFilter;

        Vector edge1 = vertices[1].subtract(vertices[0]);
        Vector edge2 = vertices[2].subtract(vertices[0]);
        Vector perpendicular = edge1.cross(edge2);
        this.surfaceNormal = UnitVector.construct(perpendicular);
    }
}
