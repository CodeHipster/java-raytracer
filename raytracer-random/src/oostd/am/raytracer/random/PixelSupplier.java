package oostd.am.raytracer.random;

import oostd.am.raytracer.api.camera.Color;
import oostd.am.raytracer.api.camera.Pixel;
import oostd.am.raytracer.api.camera.Resolution;
import oostd.am.raytracer.api.geography.PixelPosition;

import java.util.Random;
import java.util.concurrent.Flow;

//supply random pixels
public class PixelSupplier implements Runnable {

    private int width, height;
    private Flow.Publisher<Pixel> target;

    public PixelSupplier(Flow.Publisher<Pixel> target, Resolution resolution){
        this.target = target;
        this.width = resolution.width;
        this.height = resolution.height;
    }

    @Override
    public void run() {
        final Random random = new Random();

        while(true){
            Pixel pixel = new Pixel(
                    new PixelPosition(random.nextInt(width), random.nextInt(height)),
                    new Color(random.nextInt(255), random.nextInt(255), random.nextInt(255))
            );
            System.out.println("Submitting pixel");
            target.
            target.submit(pixel);
        }
    }
}
