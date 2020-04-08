package oostd.am.raytracer.streaming.pipeline;

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

    private final SubmissionPublisher<InverseRay> output;
    private InverseRayCaster inverseRayCaster;

    /**
     * subscribe an inverseRay subscriber and a lightRay subscriber
     */
    public InverseRayCastProcessor(SubmissionPublisher<InverseRay> inverseRayPublisher, InverseRayCaster inverseRayCaster){
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

    /**
     * TODO: when do we decide we are done and signal onComplete?
     * TODO: What happens if we run out of resources, deadlock(submit will block, as it indirectly inserts into this)? Should we build in a timer?
     */
    @Override
    public void onNext(Collision<InverseRay> collision) {
        List<InverseRay> inverseRays = inverseRayCaster.castRay(collision);
        System.out.println("InverseRayCastProcessor: inverseRays: " + inverseRays);
        inverseRays.forEach(output::submit);
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
