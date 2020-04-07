package oostd.am.raytracer.streaming;

import oostd.am.raytracer.api.PixelSubscriberFactory;
import oostd.am.raytracer.api.camera.PixelSubscriber;
import oostd.am.raytracer.api.scenery.Scene;
import oostd.am.raytracer.streaming.tracer.*;

import java.util.concurrent.SubmissionPublisher;

public class RayTracerService implements oostd.am.raytracer.api.RayTracerService {

    Tracer tracer;

    @Override
    public void startRendering(Scene scene, PixelSubscriberFactory pixelSubscriberFactory) {
        if (tracer != null) {
            System.out.println("rendering already started, not starting again.");
        } else {
            PixelSubscriber renderSubscriber = pixelSubscriberFactory.createRenderSubscriber(scene.renderCamera.lens.name);

            SubmissionPublisher<InverseRay> inverseRayPublisher = new SubmissionPublisher<>();

            DepthController depthController = new DepthController(5, new SubmissionPublisher<>());
            LightRayProcessor lightRayProcessor = new LightRayProcessor(new SubmissionPublisher<>());
            RayCaster rayCaster = new RayCaster(new SubmissionPublisher<>(), new SubmissionPublisher<>());

            /*
            inverseRayPublisher -> depthController -> rayCaster ->
                -> depthController(loop again)
                -> lightRayProcessor -> renderSubscriber
             */
            inverseRayPublisher.subscribe(depthController);
            depthController.subscribe(rayCaster);
            rayCaster.subscribeInverseRay(depthController);

            rayCaster.subscribeLightRay(lightRayProcessor);
            lightRayProcessor.subscribe(renderSubscriber);

            RayInitializer rayInitializer = new RayInitializer(scene.renderCamera, renderSubscriber.getResolution(), inverseRayPublisher);

            rayInitializer.initialize();
        }
    }
}

