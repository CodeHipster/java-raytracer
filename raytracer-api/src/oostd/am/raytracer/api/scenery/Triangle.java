package oostd.am.raytracer.api.scenery;

import oostd.am.raytracer.api.geography.UnitVector;
import oostd.am.raytracer.api.geography.Vector;

public final class Triangle extends SceneObject{
    public Vector[] vertices;
    public UnitVector surfaceNormal;

    public Triangle(Vector[] vertices, Material material, VolumeProperties volumeProperties){
        super(material, volumeProperties);
        if(vertices.length != 3) throw new IllegalArgumentException("exactly 3 vertices required in a triangle.");
        this.vertices = vertices;

        Vector edge1 = vertices[1].subtract(vertices[0]);
        Vector edge2 = vertices[2].subtract(vertices[0]);
        Vector perpendicular = edge1.cross(edge2);
        this.surfaceNormal = new UnitVector(perpendicular);
    }
}
