package oostd.am.raytracer;

import oostd.am.raytracer.api.camera.Camera;
import oostd.am.raytracer.api.camera.Color;
import oostd.am.raytracer.api.camera.Pixel;
import oostd.am.raytracer.api.camera.Position;
import oostd.am.raytracer.api.scenery.Scenery;

import java.util.stream.Stream;

public class RayTracer implements oostd.am.raytracer.api.RayTracer {
    @Override
    public Stream<Pixel> render(Scenery scenery, Camera camera) {
        System.out.println("Logging from inside the renderer.");

        Stream<Pixel> pixelStream = Stream.generate(() -> {
            Pixel pixel = new Pixel();
            pixel.color = new Color();
            pixel.position = new Position();
            return pixel;
        });
        return pixelStream;
    }
}
