package oostd.am.raytracer.random;

import oostd.am.raytracer.api.debug.DebugWindow;
import oostd.am.raytracer.api.scenery.Scene;

/**
 * raytracer implementation that does not trace rays, but instead returns random colors for random pixels.
 */
public class RayTracerService implements oostd.am.raytracer.api.RayTracerService {

    @Override
    public void startRendering(Scene scene) {
        PixelSupplier pixelSupplier = new PixelSupplier(scene.getRenderCamera().resolution);
        pixelSupplier.subscribe(scene.getRenderCamera().outputConsumer);
        Thread pixelThread = new Thread(pixelSupplier);
        pixelThread.start();

        for(DebugWindow debugWindow: scene.getDebugWindows()){
            LineSupplier publisher = new LineSupplier();
            publisher.subscribe(debugWindow);
            Thread thread = new Thread(publisher);
            thread.start();
        }

        System.out.println("Started generating pixels and lines in separate thread.");
    }
}
