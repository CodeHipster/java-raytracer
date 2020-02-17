package oostd.am.raytracer.random;

import oostd.am.raytracer.api.camera.Color;
import oostd.am.raytracer.api.debug.Line;
import oostd.am.raytracer.api.geography.Vector;

import java.util.Random;
import java.util.concurrent.Flow;
import java.util.concurrent.SubmissionPublisher;

//supply random lines
public class LineSupplier implements Runnable, Flow.Publisher<Line> {

    private SubmissionPublisher<Line> publisher = new SubmissionPublisher<>();

    public LineSupplier() {

    }

    @Override
    public void run() {
        final Random random = new Random();

        while (true) {
            //random from to, within width height bounds

            int x1 = random.nextInt() % 10;
            int y1 = random.nextInt() % 10;
            int z1 = random.nextInt() % 10;
            int x2 = random.nextInt() % 10;
            int y2 = random.nextInt() % 10;
            int z2 = random.nextInt() % 10;
            Line debugLine = new Line(new Vector(x1, y1, z1), new Vector(x2, y2, z2)
                    , new Color(random.nextInt(255), random.nextInt(255), random.nextInt(255)));
            System.out.println("Submitting pixel");
            publisher.submit(debugLine);
        }
    }

    @Override
    public void subscribe(Flow.Subscriber<? super Line> subscriber) {
        publisher.subscribe(subscriber);
    }
}
