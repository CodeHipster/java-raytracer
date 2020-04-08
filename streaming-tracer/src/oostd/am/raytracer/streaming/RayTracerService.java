package oostd.am.raytracer.streaming;

import oostd.am.raytracer.api.PixelSubscriberFactory;
import oostd.am.raytracer.api.camera.PixelSubscriber;
import oostd.am.raytracer.api.scenery.Scene;
import oostd.am.raytracer.streaming.pipeline.CollisionProcessor;
import oostd.am.raytracer.streaming.pipeline.DepthProcessor;
import oostd.am.raytracer.streaming.pipeline.LightRayCastProcessor;
import oostd.am.raytracer.streaming.pipeline.InverseRayCastProcessor;
import oostd.am.raytracer.streaming.pipeline.LightRayPixelProcessor;
import oostd.am.raytracer.streaming.pipeline.LightRayShadowProcessor;
import oostd.am.raytracer.streaming.tracer.Collider;
import oostd.am.raytracer.streaming.tracer.InverseRay;
import oostd.am.raytracer.streaming.tracer.InverseRayCaster;
import oostd.am.raytracer.streaming.tracer.LightRayCaster;
import oostd.am.raytracer.streaming.tracer.RayInitializer;

import java.util.concurrent.SubmissionPublisher;

public class RayTracerService implements oostd.am.raytracer.api.RayTracerService {

    @Override
    public void startRendering(Scene scene, PixelSubscriberFactory pixelSubscriberFactory) {
        Collider collider = new Collider(scene.triangles);
        InverseRayCaster inverseRayCaster = new InverseRayCaster();
        LightRayCaster lightRayCaster = new LightRayCaster(scene.pointLights);

        PixelSubscriber renderSubscriber = pixelSubscriberFactory.createRenderSubscriber(scene.renderCamera.lens.name);

        SubmissionPublisher<InverseRay> inverseRayPublisher = new SubmissionPublisher<>();

        //TODO: refactor, implement some sort of abstract class for the processors?
        DepthProcessor depthProcessor = new DepthProcessor(5, 0.0001, new SubmissionPublisher<>());
        CollisionProcessor<InverseRay> inverseRayCollisionProcessor = new CollisionProcessor<>(collider, new SubmissionPublisher<>());
        InverseRayCastProcessor inverseRayCastProcessor = new InverseRayCastProcessor(new SubmissionPublisher<>(), inverseRayCaster);
        LightRayCastProcessor lightRayCastProcessor = new LightRayCastProcessor(new SubmissionPublisher<>(), lightRayCaster);
        LightRayShadowProcessor lightRayShadowProcessor = new LightRayShadowProcessor(new SubmissionPublisher<>(), collider);
        LightRayPixelProcessor lightRayPixelProcessor = new LightRayPixelProcessor(new SubmissionPublisher<>());
            /*
            inverseRayPublisher -> depth -> collision   -> inverseRayCaster -> depth (loop back)
                                                        -> lightRayCaster -> shadow -> render -> other system
             */
        inverseRayPublisher.subscribe(depthProcessor);
        depthProcessor.subscribe(inverseRayCollisionProcessor);
        inverseRayCollisionProcessor.subscribe(inverseRayCastProcessor);
        inverseRayCollisionProcessor.subscribe(lightRayCastProcessor);
        inverseRayCastProcessor.subscribe(depthProcessor);
        lightRayCastProcessor.subscribe(lightRayShadowProcessor);
        lightRayShadowProcessor.subscribe(lightRayPixelProcessor);
        lightRayPixelProcessor.subscribe(renderSubscriber);

        // put initial rays into the pipeline
        RayInitializer rayInitializer = new RayInitializer(scene.renderCamera, renderSubscriber.getResolution(), inverseRayPublisher);
        rayInitializer.initialize();
    }
}

