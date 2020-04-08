package oostd.am.raytracer.streaming.pipeline;

import oostd.am.raytracer.streaming.tracer.Collider;
import oostd.am.raytracer.streaming.tracer.Collision;
import oostd.am.raytracer.streaming.tracer.LightRay;

import java.util.concurrent.Flow;
import java.util.concurrent.SubmissionPublisher;

public class LightRayShadowProcessor implements Flow.Processor<LightRay, LightRay> {
    private SubmissionPublisher<LightRay> output;
    private Collider collider;

    public LightRayShadowProcessor(SubmissionPublisher<LightRay> output, Collider collider) {
        this.output = output;
        this.collider = collider;
    }

    @Override
    public void subscribe(Flow.Subscriber<? super LightRay> subscriber) {
        output.subscribe(subscriber);
    }

    @Override
    public void onSubscribe(Flow.Subscription subscription) {
        subscription.request(Long.MAX_VALUE);
    }

    @Override
    public void onNext(LightRay lightRay) {
        //TODO: shadow calculator
        Collision<LightRay> collide = collider.collide(lightRay);
        if(collide != null){
            double distanceToImpact = lightRay.position.subtract(collide.impactPoint).length();
            double distanceToLight = lightRay.position.subtract(lightRay.light.position).length();
            if(distanceToLight < distanceToImpact){
                //Nothing is blocking the lightRay
                System.out.println("LightRayShadowProcessor ray: "+ lightRay +" reaches light");
                output.submit(lightRay);
            }
        }else{
            //Nothing is blocking the lightRay
            System.out.println("LightRayShadowProcessor ray: "+ lightRay +" reaches light");
            output.submit(lightRay);
        }
        System.out.println("LightRayShadowProcessor ray: "+ lightRay +" does NOT reach light");
    }

    @Override
    public void onError(Throwable throwable) {
        System.out.println("Error in LightRayShadowProcessor.");
    }

    @Override
    public void onComplete() {
        System.out.println("LightRayShadowProcessor complete.");
    }
}
