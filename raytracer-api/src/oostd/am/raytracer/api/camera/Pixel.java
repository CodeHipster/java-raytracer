package oostd.am.raytracer.api.camera;

public class Pixel {
    public Position position;
    public Color color;

    public Pixel(Position position, Color color){
        this.position = position;
        this.color = color;
    }
}
