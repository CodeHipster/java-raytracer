package oostd.am.raytracer.api.camera;

import oostd.am.raytracer.api.geography.PixelPosition;

public class Pixel {
    public PixelPosition position;
    public Color color;

    public Pixel(PixelPosition position, Color color){
        this.position = position;
        this.color = color;
    }
}
