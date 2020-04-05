package oostd.am.raytracer.engine.debug;

import oostd.am.raytracer.api.PixelSubscriberFactory;
import oostd.am.raytracer.api.camera.Color;
import oostd.am.raytracer.api.camera.PixelSubscriber;
import oostd.am.raytracer.api.debug.Line;
import oostd.am.raytracer.api.debug.Window;
import oostd.am.raytracer.api.geography.Vector;
import oostd.am.raytracer.api.scenery.Triangle;
import oostd.am.raytracer.engine.Collision;
import oostd.am.raytracer.engine.Ray;
import oostd.am.raytracer.engine.LightRay;

import java.util.List;
import java.util.concurrent.SubmissionPublisher;

public class Debugger {

    private SubmissionPublisher<Line> debugLineOutput;

    public Debugger(List<Window> debugWindows, PixelSubscriberFactory pixelSubscriberFactory){
        this.debugLineOutput = new SubmissionPublisher<>();

        debugWindows.forEach(window -> {
            // consumes Pixels
            PixelSubscriber pixelSubscriber = pixelSubscriberFactory.createDebugSubscriber(window.name);
            // consumes Lines, produces Pixels
            DebugLineProcessor lineToPixelProcessor = new DebugLineProcessor(window, pixelSubscriber.getResolution());
            // hook up pixel consumer to pixel producer
            lineToPixelProcessor.subscribe(pixelSubscriber);
            // hook up line consumer to line producer
            debugLineOutput.subscribe(lineToPixelProcessor);
        });
    }


    /**
     * output lines for geometry
     */
    public void drawSceneGeometry(List<Triangle> triangles) {
        triangles.forEach(triangle -> {
            debugLineOutput.submit(new Line(triangle.vertices[0], triangle.vertices[1], Color.WHITE));
            debugLineOutput.submit(new Line(triangle.vertices[1], triangle.vertices[2], Color.WHITE));
            debugLineOutput.submit(new Line(triangle.vertices[2], triangle.vertices[0], Color.WHITE));
        });
        // print axis
        debugLineOutput.submit(new Line(
                new Vector(0, 0, 0),
                new Vector(1, 0, 0),
                Color.RED));
        debugLineOutput.submit(new Line(
                new Vector(0, 0, 0),
                new Vector(0, 1, 0),
                Color.GREEN));
        debugLineOutput.submit(new Line(
                new Vector(0, 0, 0),
                new Vector(0, 0, 1),
                Color.BLUE));
    }

    public void line(Ray ray, Collision collision, Triangle target){
        debugLineOutput.submit(new Line(ray.position, collision.impactPoint, target.material.colorFilter.filter(Color.WHITE)));
    }

    public void line(LightRay lightRay){
        debugLineOutput.submit(new Line(lightRay.position, lightRay.light.position, lightRay.light.color));
    }

    public void close(){
        debugLineOutput.close();
    }
}
