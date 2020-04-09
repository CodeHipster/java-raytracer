package oostd.am.raytracer.streaming.debug;

import oostd.am.raytracer.api.PixelSubscriberFactory;
import oostd.am.raytracer.api.camera.Color;
import oostd.am.raytracer.api.camera.PixelSubscriber;
import oostd.am.raytracer.api.debug.Line;
import oostd.am.raytracer.api.debug.Window;
import oostd.am.raytracer.api.geography.PixelPosition;
import oostd.am.raytracer.api.geography.Vector;
import oostd.am.raytracer.api.scenery.Triangle;
import oostd.am.raytracer.streaming.tracer.Collision;
import oostd.am.raytracer.streaming.tracer.InverseRay;
import oostd.am.raytracer.streaming.tracer.LightRay;

import java.util.List;
import java.util.concurrent.SubmissionPublisher;

public class Debugger {

    private SubmissionPublisher<Line> debugLineOutput;
    private final PixelPosition pixelToTrace;

    public Debugger(List<Window> debugWindows, PixelSubscriberFactory pixelSubscriberFactory, SubmissionPublisher<Line> debugLineOutput, PixelPosition pixelToTrace) {
        this.debugLineOutput = debugLineOutput;
        this.pixelToTrace = pixelToTrace;

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

    public void line(Collision<InverseRay> collision) {
        if (pixelToTrace.equals(collision.ray.pixelPosition))
            debugLineOutput.submit(new Line(collision.ray.position, collision.impactPoint, collision.target.material.colorFilter.filter(Color.WHITE)));
    }

    public void line(LightRay lightRay) {
        if (pixelToTrace.equals(lightRay.pixelPosition))
            debugLineOutput.submit(new Line(lightRay.position, lightRay.light.position, lightRay.light.color));
    }

    /**
     * output a line to object causing shadow.
     *
     * @param lightRay
     * @param distanceToImpact
     */
    public void line(LightRay lightRay, double distanceToImpact) {
        if (pixelToTrace.equals(lightRay.pixelPosition)) {
            Vector to = lightRay.direction.scale(distanceToImpact).addSelf(lightRay.position);
            this.line(new Line(lightRay.position, to, new Color(0.1, 0.1, 0.1)));
        }
    }

    public void line(Line line) {
        debugLineOutput.submit(line);
    }

    public void close() {
        debugLineOutput.close();
    }
}
