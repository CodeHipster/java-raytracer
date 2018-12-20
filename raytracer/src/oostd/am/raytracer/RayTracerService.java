package oostd.am.raytracer;

import oostd.am.raytracer.api.camera.Camera;
import oostd.am.raytracer.api.camera.Pixel;
import oostd.am.raytracer.api.debug.DebugLine;
import oostd.am.raytracer.api.scenery.Scene;

import java.util.concurrent.Flow;
import java.util.concurrent.SubmissionPublisher;

public class RayTracerService implements oostd.am.raytracer.api.RayTracerService {

    private SubmissionPublisher<Pixel> pixelSink;
    private SubmissionPublisher<DebugLine> lineSink;

    public RayTracerService(){
        this.pixelSink = new SubmissionPublisher<>();
        this.lineSink = new SubmissionPublisher<>();
    }

    @Override
    public void startRendering(Flow.Subscriber<Pixel> subscriber, Scene scene, Camera camera) {
        this.startRendering(subscriber, null, scene, camera);
    }

    @Override
    public void startRendering(Flow.Subscriber<Pixel> subscriber, Flow.Subscriber<DebugLine> debugSubcriber, Scene scene, Camera camera) {

        if(debugSubcriber != null) {lineSink.subscribe(debugSubcriber);}
        pixelSink.subscribe(subscriber);
        Engine engine = new Engine(camera, scene, pixelSink, lineSink);

        new Thread(engine).start();
    }
}
