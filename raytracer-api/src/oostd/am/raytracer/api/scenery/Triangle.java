package oostd.am.raytracer.api.scenery;

public class Triangle {
    public Vertex[] vertices;

    public Triangle(Vertex[] vertices){
        if(vertices.length != 3) throw new IllegalArgumentException("exactly 3 vertices required in a triangle.");
        this.vertices = vertices;
    }
}
