package oostd.am.raytracer.api.scenery;

import oostd.am.raytracer.api.camera.Color;

public class Triangle {
    public Vertex[] vertices;
    public Color color;

    public Triangle(Vertex[] vertices, Color color){
        if(vertices.length != 3) throw new IllegalArgumentException("exactly 3 vertices required in a triangle.");
        this.vertices = vertices;
        this.color = color;
    }
}
