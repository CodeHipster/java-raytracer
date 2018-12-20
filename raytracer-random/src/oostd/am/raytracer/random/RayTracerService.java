package oostd.am.raytracer.random;

import oostd.am.raytracer.api.camera.Camera;
import oostd.am.raytracer.api.camera.Pixel;
import oostd.am.raytracer.api.debug.DebugLine;
import oostd.am.raytracer.api.scenery.Scene;

import java.util.concurrent.Flow;
import java.util.concurrent.SubmissionPublisher;

/**
 * raytracer implementation that does not trace rays, but instead returns random colors for random pixels.
 */
public class RayTracerService implements oostd.am.raytracer.api.RayTracerService {

    private SubmissionPublisher<Pixel> pixelPusher;
    private SubmissionPublisher<DebugLine> linePusher;

    public RayTracerService(){
        this.pixelPusher = new SubmissionPublisher<>();
        this.linePusher = new SubmissionPublisher<>();
    }

    @Override
    public void startRendering(Flow.Subscriber<Pixel> subscriber, Scene scene, Camera camera) {

    }

    @Override
    public void startRendering(Flow.Subscriber<Pixel> subscriber, Flow.Subscriber<DebugLine> debugSubcriber, Scene scene, Camera camera) {

        System.out.println("Logging from inside the renderer.");

        pixelPusher.subscribe(subscriber);
        linePusher.subscribe(debugSubcriber);

        Thread pixelThread = new Thread(new PixelSupplier(pixelPusher, camera.lens.width, camera.lens.height));
        pixelThread.start();
        Thread lineThread = new Thread(new LineSupplier(linePusher));
        lineThread.start();

        System.out.println("Started generating pixels in separate thread.");
    }
}
