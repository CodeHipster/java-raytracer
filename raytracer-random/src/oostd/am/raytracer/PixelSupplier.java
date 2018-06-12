package oostd.am.raytracer;

import oostd.am.raytracer.api.camera.Color;
import oostd.am.raytracer.api.camera.Pixel;
import oostd.am.raytracer.api.geography.PixelPosition;

import java.util.Random;
import java.util.concurrent.SubmissionPublisher;

//supply random pixels
public class PixelSupplier implements Runnable {

    private int width, height;
    private SubmissionPublisher<Pixel> target;

    public PixelSupplier(SubmissionPublisher<Pixel> target, int width, int height){
        this.target = target;
        this.width = width;
        this.height = height;
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
            target.submit(pixel);
        }
    }
}
