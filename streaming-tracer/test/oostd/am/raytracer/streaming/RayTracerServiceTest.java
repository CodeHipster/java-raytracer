package oostd.am.raytracer.streaming;

import oostd.am.raytracer.api.PixelSubscriberFactory;
import oostd.am.raytracer.api.camera.Pixel;
import oostd.am.raytracer.api.camera.PixelSubscriber;
import oostd.am.raytracer.api.camera.Resolution;

import java.util.concurrent.Flow;

class RayTracerServiceTest {

    public static void main(String[] args) throws InterruptedException {
        RayTracerService rayTracerService = new RayTracerService();
        rayTracerService.startRendering(new TetraHedron().getScene(), new PixelSubscriberFactory() {
            @Override
            public PixelSubscriber createRenderSubscriber(String name) {
                PixelSubscriber pixelSubscriber = new PixelSubscriber() {
                    private int count = 0;

                    @Override
                    public Resolution getResolution() {
                        return new Resolution(1, 1);
                    }

                    @Override
                    public String getName() {
                        return "renderer";
                    }

                    @Override
                    public void onSubscribe(Flow.Subscription subscription) {
                        subscription.request(Long.MAX_VALUE);
                    }

                    @Override
                    public void onNext(Pixel item) {
                        System.out.println("Pixel:" + ++count);
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        System.out.println("Renderer error:");
                    }

                    @Override
                    public void onComplete() {
                        System.out.println("Renderer complete:");
                    }
                };
                return pixelSubscriber;
            }

            @Override
            public PixelSubscriber createDebugSubscriber(String name) {
                return null;
            }
        });
        Thread.sleep(10000);
    }
}