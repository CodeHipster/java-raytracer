package oostd.am.raytracer;

import oostd.am.raytracer.api.camera.*;
import oostd.am.raytracer.api.geography.UnitVector;
import oostd.am.raytracer.api.geography.Vector;
import oostd.am.raytracer.api.scenery.PointLight;
import oostd.am.raytracer.api.scenery.Scene;
import oostd.am.raytracer.api.scenery.Triangle;
import oostd.am.raytracer.api.scenery.Vertex;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.concurrent.Flow;
import java.util.concurrent.SubmissionPublisher;


public class EngineTest {

    @Test
    public void start() {

        SubmissionPublisher<Pixel> publisher = new SubmissionPublisher<>();
        Flow.Subscriber<Pixel> subscriber = new TestSubscriber();
        publisher.subscribe(subscriber);

        Scene scene = new Scene(
                Arrays.asList(
                        new Triangle(new Vertex[]{
                                new Vertex(-1.5f, 2, 12),
                                new Vertex(1, 1, 11),
                                new Vertex(-1, -1, 10)
                        },
                                new Color(0, 255, 0))),
                Arrays.asList(new PointLight(new Vertex(0, 5, 8))));
        Camera camera = new Camera(
                new Positioning(
                        new Vector(0, 0, 0), //Camera at the center of the scene
                        UnitVector.construct(0, 0, 1)) //Camera pointing 'forward' into the scene
                , new Lens(300, 300, 1));
        Engine engine = new Engine(camera, scene, publisher);
        engine.start();
    }


    public static class TestSubscriber implements Flow.Subscriber<Pixel> {

        //TODO: make it some generic drawable?
        private Flow.Subscription subscription;

        public TestSubscriber() {
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

            System.out.println("received pixel: " + pixel.toString());
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
}
