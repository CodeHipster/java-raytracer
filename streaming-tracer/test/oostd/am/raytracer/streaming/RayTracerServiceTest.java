package oostd.am.raytracer.streaming;

import oostd.am.raytracer.api.PixelSubscriberFactory;
import oostd.am.raytracer.api.camera.Camera;
import oostd.am.raytracer.api.camera.Pixel;
import oostd.am.raytracer.api.camera.PixelSubscriber;
import oostd.am.raytracer.api.camera.Resolution;
import oostd.am.raytracer.api.geography.UnitVector;
import oostd.am.raytracer.api.geography.Vector;
import oostd.am.raytracer.api.scenery.Scene;

import java.util.concurrent.Flow;

class RayTracerServiceTest {

    public static void main(String[] args) throws InterruptedException {
        RayTracerService rayTracerService = new RayTracerService();
        Camera renderCamera = new Camera(new Vector(1,1,1), new UnitVector(1,1,1), new UnitVector(1,1,1), 1, "renderCamera");
        Scene scene = new Scene(null, null, renderCamera, null);
        rayTracerService.startRendering(scene, new PixelSubscriberFactory() {
            @Override
            public PixelSubscriber createRenderSubscriber(String name) {
                PixelSubscriber pixelSubscriber = new PixelSubscriber() {
                    private Flow.Subscription subscription;
                    private int count = 0;

                    @Override
                    public Resolution getResolution() {
                        return new Resolution(4, 4);
                    }

                    @Override
                    public String getName() {
                        return "renderer";
                    }

                    @Override
                    public void onSubscribe(Flow.Subscription subscription) {
                        this.subscription = subscription;
                        subscription.request(1);
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