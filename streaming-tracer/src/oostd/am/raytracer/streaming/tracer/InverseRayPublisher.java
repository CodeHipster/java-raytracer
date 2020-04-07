package oostd.am.raytracer.streaming.tracer;

import java.util.concurrent.Flow;
import java.util.concurrent.SubmissionPublisher;

public class InverseRayPublisher extends SubmissionPublisher<InverseRay> implements Flow.Subscriber<InverseRay> {

    private Flow.Subscription subscription;

    @Override
    public void onSubscribe(Flow.Subscription subscription) {
        this.subscription = subscription;
    }

    @Override
    public void onNext(InverseRay item) {
        submit(item);
        subscription.request(1);
    }

    @Override
    public void onError(Throwable throwable) {
        System.out.println("InverseRayPublisher error.");
    }

    @Override
    public void onComplete() {
        System.out.println("InverseRayPublisher complete.");
    }
}
