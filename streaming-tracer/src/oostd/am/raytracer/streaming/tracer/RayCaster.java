package oostd.am.raytracer.streaming.tracer;

import oostd.am.raytracer.api.camera.Color;
import oostd.am.raytracer.api.camera.Pixel;
import oostd.am.raytracer.api.geography.Vector;
import oostd.am.raytracer.api.scenery.PointLight;

import java.util.concurrent.Flow;
import java.util.concurrent.SubmissionPublisher;

public class RayCaster implements Flow.Subscriber<InverseRay> {

    private final SubmissionPublisher<InverseRay> inverseRayPublisher;
    private final SubmissionPublisher<LightRay> lightRayPublisher;
    private Flow.Subscription subscription;

    /**
     * subscribe an inverseRay subscriber and a lightRay subscriber
     */
    public RayCaster(SubmissionPublisher<InverseRay> inverseRayPublisher, SubmissionPublisher<LightRay> lightRayPublisher){
        this.inverseRayPublisher = inverseRayPublisher;
        this.lightRayPublisher = lightRayPublisher;
    }

    public void subscribeInverseRay(Flow.Subscriber<? super InverseRay> subscriber){
        inverseRayPublisher.subscribe(subscriber);
    }

    public void subscribeLightRay(Flow.Subscriber<? super LightRay> subscriber){
        lightRayPublisher.subscribe(subscriber);
    }

    @Override
    public void onSubscribe(Flow.Subscription subscription) {
        this.subscription = subscription;
        subscription.request(1);
    }

    @Override
    public void onNext(InverseRay ray) {
        System.out.println("Raycaster, cast ray with depth: " + ray.depth);
        InverseRay inverseRay = new InverseRay(ray.depth + 1, 1, null, null, null, null);
        inverseRayPublisher.submit(inverseRay);
        PointLight light = new PointLight(new Vector(0,0,0), new Color(1,1,1));
        LightRay lightRay = new LightRay(light, null, new Vector(0,0,0), null, 1);
        lightRayPublisher.submit(lightRay);
        subscription.request(1);
    }

    @Override
    public void onError(Throwable throwable) {
        //TODO: close?
        System.out.println("Error in raycaster.");
    }

    @Override
    public void onComplete() {
        //TODO: close?
        System.out.println("Raycaster complete.");
    }
}
