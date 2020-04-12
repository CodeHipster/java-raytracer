package oostd.am.raytracer.streaming.pipeline;

import oostd.am.raytracer.streaming.tracer.Collision;
import oostd.am.raytracer.streaming.tracer.InverseRay;
import oostd.am.raytracer.streaming.tracer.LightRay;
import oostd.am.raytracer.streaming.tracer.LightRayCaster;

import java.util.List;
import java.util.concurrent.Flow;
import java.util.concurrent.SubmissionPublisher;

/**
 * Processor that processes a collision of an InverseRay into multiple LightRays.
 */
public class LightRayCastProcessor implements Flow.Processor<Collision<InverseRay>, LightRay> {

    private final SubmissionPublisher<LightRay> output;
    private final LightRayCaster lightRayCaster;

    public LightRayCastProcessor(SubmissionPublisher<LightRay> output, LightRayCaster lightRayCaster) {
        this.output = output;
        this.lightRayCaster = lightRayCaster;
    }

    @Override
    public void onSubscribe(Flow.Subscription subscription) {
        subscription.request(Long.MAX_VALUE);
    }

    @Override
    public void onNext(Collision<InverseRay> collision) {
        List<LightRay> lightRays = lightRayCaster.cast(collision);
        lightRays.forEach(item -> {
            int lag = output.submit(item);
        });
    }

    @Override
    public void onError(Throwable throwable) {
        System.out.println("Error in LightRayCastProcessor.");
        throw new RuntimeException(throwable);
    }

    @Override
    public void onComplete() {
        System.out.println("LightRayCastProcessor complete.");
    }

    @Override
    public void subscribe(Flow.Subscriber<? super LightRay> subscriber) {
        output.subscribe(subscriber);
    }
}
