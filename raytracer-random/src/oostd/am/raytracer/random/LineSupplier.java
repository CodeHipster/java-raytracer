package oostd.am.raytracer.random;

import oostd.am.raytracer.api.camera.Color;
import oostd.am.raytracer.api.camera.Pixel;
import oostd.am.raytracer.api.debug.DebugLine;
import oostd.am.raytracer.api.geography.PixelPosition;
import oostd.am.raytracer.api.geography.Vector;

import java.util.Random;
import java.util.concurrent.SubmissionPublisher;

//supply random pixels
public class LineSupplier implements Runnable {

    private SubmissionPublisher<DebugLine> target;

    public LineSupplier(SubmissionPublisher<DebugLine> target){
        this.target = target;
    }

    @Override
    public void run() {
        final Random random = new Random();

        while(true){
            //random from to, within width height bounds

            int x1 = random.nextInt() % 10;
            int y1 = random.nextInt() % 10;
            int z1 = random.nextInt() % 10;
            int x2 = random.nextInt() % 10;
            int y2 = random.nextInt() % 10;
            int z2 = random.nextInt() % 10;
            DebugLine debugLine = new DebugLine(new Vector(x1, y1, z1), new Vector(x2, y2, z2), random.nextDouble());
            System.out.println("Submitting pixel");
            target.submit(debugLine);
        }
    }
}
