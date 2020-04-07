package oostd.am.raytracer.streaming.tracer;

import oostd.am.raytracer.api.camera.Color;
import oostd.am.raytracer.api.camera.Pixel;
import oostd.am.raytracer.api.geography.PixelPosition;

import java.util.concurrent.Flow;
import java.util.concurrent.SubmissionPublisher;

public class LightRayProcessor implements Flow.Processor<LightRay, Pixel> {

    private final SubmissionPublisher<Pixel> output;
    private Flow.Subscription subscription;

    public LightRayProcessor(SubmissionPublisher<Pixel> output) {
        this.output = output;
    }

    @Override
    public void subscribe(Flow.Subscriber<? super Pixel> subscriber) {
        output.subscribe(subscriber);
    }

    @Override
    public void onSubscribe(Flow.Subscription subscription) {
        this.subscription = subscription;
        this.subscription.request(1);
    }

    @Override
    public void onNext(LightRay item) {
        Pixel p = new Pixel(new PixelPosition(0,0), new Color(0,0,0));
        output.submit(p);
        subscription.request(1);
    }

    @Override
    public void onError(Throwable throwable) {
        System.out.println("Error in LightRayProcessor.");
    }

    @Override
    public void onComplete() {
        System.out.println("LightRayProcessor complete.");
    }
}
