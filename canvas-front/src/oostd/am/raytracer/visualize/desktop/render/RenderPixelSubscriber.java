package oostd.am.raytracer.visualize.desktop.render;

import oostd.am.raytracer.api.camera.Pixel;

import java.util.concurrent.Flow;

public class RenderPixelSubscriber implements Flow.Subscriber<Pixel> {

    //TODO: make it some generic drawable?
    private RenderFrame target;
    private Flow.Subscription subscription;

    public RenderPixelSubscriber(RenderFrame target){
        this.target = target;

        System.out.println("constructed subscriber");
    }

    @Override
    public void onSubscribe(Flow.Subscription subscription) {
        this.subscription = subscription;
        subscription.request(1);
        System.out.println("subscribed: " + subscription);
    }

    @Override
    public void onNext(Pixel pixel) {

        System.out.println("received pixel");
        target.drawPixel(pixel);
        subscription.request(1);
    }

    @Override
    public void onError(Throwable throwable) {
        System.out.println("subscriber error occured: " + throwable);
        throw new RuntimeException(throwable);
    }

    @Override
    public void onComplete() {

        System.out.println("subscriber completed");
    }
}
