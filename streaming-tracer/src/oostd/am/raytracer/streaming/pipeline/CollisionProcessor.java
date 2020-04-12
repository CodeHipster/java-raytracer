package oostd.am.raytracer.streaming.pipeline;

import oostd.am.raytracer.streaming.tracer.Collider;
import oostd.am.raytracer.streaming.tracer.Collision;
import oostd.am.raytracer.streaming.tracer.Ray;

import java.util.concurrent.Flow;
import java.util.concurrent.SubmissionPublisher;

/**
 * A Flow.Processor that finds a collision point for a ray.
 */
public class CollisionProcessor<R extends Ray> implements Flow.Processor<R, Collision<R>> {

    private final Collider collider;
    private final SubmissionPublisher<Collision<R>> output;

    public CollisionProcessor(Collider collider, SubmissionPublisher<Collision<R>> output) {
        this.collider = collider;
        this.output = output;
    }

    @Override
    public void subscribe(Flow.Subscriber<? super Collision<R>> subscriber) {
        output.subscribe(subscriber);
    }

    @Override
    public void onSubscribe(Flow.Subscription subscription) {
        subscription.request(Long.MAX_VALUE);
    }

    @Override
    public void onError(Throwable throwable) {
        System.out.println("Error in CollisionProcessor.");
        throw new RuntimeException(throwable);
    }

    @Override
    public void onComplete() {
        System.out.println("CollisionProcessor complete.");
    }

    @Override
    public void onNext(R ray) {
        Collision<R> collision = collider.collide(ray);
        int lag = output.submit(collision);
    }
}
