package oostd.am.raytracer.random;

import oostd.am.raytracer.api.camera.Color;
import oostd.am.raytracer.api.camera.Pixel;
import oostd.am.raytracer.api.camera.PixelSubscriber;
import oostd.am.raytracer.api.geography.PixelPosition;

import java.util.Random;
import java.util.concurrent.Flow;
import java.util.concurrent.SubmissionPublisher;

//supply random pixels
public class PixelSupplier implements Runnable, Flow.Publisher<Pixel> {

    private int width, height;
    private SubmissionPublisher<Pixel> publisher = new SubmissionPublisher<>();

    public PixelSupplier(PixelSubscriber pixelSubscriber){
        this.width = pixelSubscriber.getResolution().width;
        this.height = pixelSubscriber.getResolution().height;
        this.subscribe(pixelSubscriber);
    }

    @Override
    public void run() {
        final Random random = new Random();

        while(true){
            Pixel pixel = new Pixel(
                    new PixelPosition(random.nextInt(width), random.nextInt(height)),
                    new Color(random.nextInt(255), random.nextInt(255), random.nextInt(255))
            );
            publisher.submit(pixel);
        }
    }

    @Override
    public void subscribe(Flow.Subscriber<? super Pixel> subscriber) {
        publisher.subscribe(subscriber);
    }
}
