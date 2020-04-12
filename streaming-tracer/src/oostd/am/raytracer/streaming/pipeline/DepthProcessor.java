package oostd.am.raytracer.streaming.pipeline;

import oostd.am.raytracer.streaming.tracer.InverseRay;

import java.util.concurrent.Flow;
import java.util.concurrent.SubmissionPublisher;

/**
 * If ray depth or ray intensity fall below given threshold, do not cast the ray.
 */
public class DepthProcessor implements Flow.Processor<InverseRay, InverseRay>{

    private final double intensityLimit;
    private final SubmissionPublisher<InverseRay> output;
    private final int limit;

    /**
     * @param depthLimit is the amount of depth a ray can have before it stops being cast further
     * @param intensityLimit is the lower bound of intensity a ray must have to be cast further.
     */
    public DepthProcessor(int depthLimit, double intensityLimit, SubmissionPublisher<InverseRay> publisher){
        this.limit = depthLimit;
        this.intensityLimit = intensityLimit;
        this.output = publisher;
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
    public void onNext(InverseRay ray) {
        if(ray.depth < limit && ray.intensity > intensityLimit){
            int lag = output.submit(ray);
        }
    }

    @Override
    public void onError(Throwable throwable) {
        System.out.println("Error in DepthProcessor.");
        throw new RuntimeException(throwable);
    }

    @Override
    public void onComplete() {
        System.out.println("DepthProcessor complete.");
    }
}
