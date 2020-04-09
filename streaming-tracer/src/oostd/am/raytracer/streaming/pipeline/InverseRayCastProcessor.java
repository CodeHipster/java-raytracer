package oostd.am.raytracer.streaming.pipeline;

import oostd.am.raytracer.streaming.debug.Debugger;
import oostd.am.raytracer.streaming.tracer.Collision;
import oostd.am.raytracer.streaming.tracer.InverseRay;
import oostd.am.raytracer.streaming.tracer.InverseRayCaster;

import java.util.List;
import java.util.concurrent.Flow;
import java.util.concurrent.SubmissionPublisher;

/**
 * Process InverseRays, new rays are published to given publishers.
 */
public class InverseRayCastProcessor implements Flow.Processor<Collision<InverseRay>, InverseRay> {

    private Debugger debugger;
    private final SubmissionPublisher<InverseRay> output;
    private InverseRayCaster inverseRayCaster;

    /**
     * subscribe an inverseRay subscriber and a lightRay subscriber
     */
    public InverseRayCastProcessor(SubmissionPublisher<InverseRay> inverseRayPublisher, InverseRayCaster inverseRayCaster, Debugger debugger){
        this.debugger = debugger;
        this.output = inverseRayPublisher;
        this.inverseRayCaster = inverseRayCaster;
    }

    @Override
    public void subscribe(Flow.Subscriber<? super InverseRay> subscriber) {
        output.subscribe(subscriber);
    }

    @Override
    public void onSubscribe(Flow.Subscription subscription) {
        subscription.request(Long.MAX_VALUE);
    }

    @Override
    public void onNext(Collision<InverseRay> collision) {
        debugger.line(collision);
        List<InverseRay> inverseRays = inverseRayCaster.castRay(collision);
        inverseRays.forEach(item -> {
            int lag = output.submit(item);
        });
    }

    @Override
    public void onError(Throwable throwable) {
        //TODO: close?
        System.out.println("Error in InverseRayCastProcessor.");
    }

    @Override
    public void onComplete() {
        //TODO: close?
        System.out.println("InverseRayCastProcessor complete.");
    }
}
