package oostd.am.raytracer.streaming.tracer;

import java.util.concurrent.Flow;
import java.util.concurrent.SubmissionPublisher;

/**
 * If ray depth or ray intensity fall below given threshold, do not cast the ray.
 */
public class DepthController implements Flow.Processor<InverseRay, InverseRay>{

    private final SubmissionPublisher<InverseRay> output;
    private Flow.Subscription subscription;
    private int limit;

    public DepthController(int limit, SubmissionPublisher<InverseRay> publisher){
        this.limit = limit;
        this.output = publisher;
    }

    @Override
    public void subscribe(Flow.Subscriber<? super InverseRay> subscriber) {
        output.subscribe(subscriber);
    }

    @Override
    public void onSubscribe(Flow.Subscription subscription) {
        this.subscription = subscription;
        this.subscription.request(Long.MAX_VALUE);
    }

    @Override
    public void onNext(InverseRay ray) {
        System.out.println("DepthController ray depth:" + ray.depth);
        if(ray.depth < limit || ray.intensity > 0.001){
            output.submit(ray);
        }else{
            System.out.println("DepthController max depth reached:" + limit);
        }
    }

    @Override
    public void onError(Throwable throwable) {
        System.out.println("Error in depth controller.");
    }

    @Override
    public void onComplete() {

        System.out.println("Depth controller complete.");
    }
}
