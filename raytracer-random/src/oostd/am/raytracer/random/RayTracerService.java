package oostd.am.raytracer.random;

import oostd.am.raytracer.api.camera.Pixel;
import oostd.am.raytracer.api.debug.DebugCamera;
import oostd.am.raytracer.api.debug.DebugWindow;
import oostd.am.raytracer.api.scenery.Scene;

import java.util.concurrent.SubmissionPublisher;

/**
 * raytracer implementation that does not trace rays, but instead returns random colors for random pixels.
 */
public class RayTracerService implements oostd.am.raytracer.api.RayTracerService {

    @Override
    public void startRendering(Scene scene) {
        System.out.println("Logging from inside the renderer.");
        SubmissionPublisher<Pixel> renderOutput = new SubmissionPublisher<>();

        renderOutput.subscribe(scene.getRenderCamera().outputConsumer);
        Thread pixelThread = new Thread(new PixelSupplier(renderOutput, scene.getRenderCamera().resolution));
        pixelThread.start();

        for(DebugWindow debugWindow: scene.getDebugWindows()){
            SubmissionPublisher<Pixel> debugOutput = new SubmissionPublisher<>();
            debugOutput.subscribe(debugWindow); //TODO: random line generator

            Thread thread = new Thread(new PixelSupplier(debugWindow, debugWindow.resolution));
            thread.start();
        }

        System.out.println("Started generating pixels in separate thread.");
    }
}
