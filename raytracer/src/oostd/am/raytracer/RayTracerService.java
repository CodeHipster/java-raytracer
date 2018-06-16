package oostd.am.raytracer;

import oostd.am.raytracer.api.camera.Camera;
import oostd.am.raytracer.api.camera.Pixel;
import oostd.am.raytracer.api.scenery.Scene;

import java.util.concurrent.Flow;
import java.util.concurrent.SubmissionPublisher;

public class RayTracerService implements oostd.am.raytracer.api.RayTracerService {

    private SubmissionPublisher<Pixel> pixelSink;

    public RayTracerService(){
        this.pixelSink = new SubmissionPublisher<>();
    }

    @Override
    public void startRendering(Flow.Subscriber<Pixel> subscriber, Scene scene, Camera camera) {
        pixelSink.subscribe(subscriber);
        Engine engine = new Engine(camera, scene, pixelSink);
        engine.start();
    }
}
