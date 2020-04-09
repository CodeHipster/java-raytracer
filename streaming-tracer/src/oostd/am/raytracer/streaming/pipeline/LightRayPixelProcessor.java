package oostd.am.raytracer.streaming.pipeline;

import oostd.am.raytracer.api.camera.Color;
import oostd.am.raytracer.api.camera.Pixel;
import oostd.am.raytracer.api.geography.PixelPosition;
import oostd.am.raytracer.streaming.tracer.LightRay;
import oostd.am.raytracer.streaming.tracer.PhongReflection;

import java.util.concurrent.Flow;
import java.util.concurrent.SubmissionPublisher;

public class LightRayPixelProcessor implements Flow.Processor<LightRay, Pixel> {

    private final SubmissionPublisher<Pixel> output;

    public LightRayPixelProcessor(SubmissionPublisher<Pixel> output) {
        this.output = output;
    }

    @Override
    public void subscribe(Flow.Subscriber<? super Pixel> subscriber) {
        output.subscribe(subscriber);
    }

    @Override
    public void onSubscribe(Flow.Subscription subscription) {
        subscription.request(Long.MAX_VALUE);
    }

    @Override
    public void onNext(LightRay lightRay) {
        Color color = PhongReflection.calculatePhong(lightRay.viewDirection, lightRay.light, lightRay.position, lightRay.triangle);
        Color scaled = color.scale(lightRay.intensity);
        int lag = output.submit(new Pixel(lightRay.pixelPosition, scaled));
    }

    @Override
    public void onError(Throwable throwable) {
        System.out.println("Error in LightRayPixelProcessor.");
    }

    @Override
    public void onComplete() {
        System.out.println("LightRayPixelProcessor complete.");
    }
}
