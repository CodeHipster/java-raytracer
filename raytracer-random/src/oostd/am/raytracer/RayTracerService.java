package oostd.am.raytracer;

import oostd.am.raytracer.api.camera.Camera;
import oostd.am.raytracer.api.camera.Pixel;
import oostd.am.raytracer.api.scenery.Scenery;

import java.util.concurrent.Flow;
import java.util.concurrent.SubmissionPublisher;

/**
 * raytracer implementation that does not trace rays, but instead returns random colors for random pixels.
 */
public class RayTracerService implements oostd.am.raytracer.api.RayTracerService {

    private SubmissionPublisher<Pixel> pixelPusher;

    public RayTracerService(){
        this.pixelPusher = new SubmissionPublisher<>();
    }

    @Override
    public void startRendering(Flow.Subscriber<Pixel> subscriber, Scenery scenery, Camera camera) {
        System.out.println("Logging from inside the renderer.");

        pixelPusher.subscribe(subscriber);

        Thread thread = new Thread(new PixelSupplier(pixelPusher, camera.lens.width, camera.lens.height));
        thread.start();

        System.out.println("Started generating pixels in separate thread.");
    }
}
