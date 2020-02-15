package oostd.am.raytracer.debug;

import oostd.am.raytracer.api.camera.Pixel;
import oostd.am.raytracer.api.camera.Resolution;
import oostd.am.raytracer.api.debug.Line;
import oostd.am.raytracer.api.debug.Line2D;
import oostd.am.raytracer.api.debug.Window;
import oostd.am.raytracer.api.geography.Vector2D;
import oostd.am.raytracer.api.scenery.Scene;

import java.util.concurrent.Flow;
import java.util.concurrent.SubmissionPublisher;

/**
 * DebugWindow wraps a DebugCamera to translate from lines to pixels.
 */
public class DebugLineProcessor implements Flow.Processor<Line, Pixel> {

    private Resolution resolution;
    private Window window;
    private SubmissionPublisher<Pixel> output;

    public DebugLineProcessor(Window window, Resolution resolution) {
        this.resolution = resolution;
        this.window = window;
        this.output = new SubmissionPublisher<>();
    }

    /**
     * draw lines for the scene geometry to the camera
     *
     * @param scene
     */
    public void renderSceneGeometry(Scene scene) {
        scene.triangles.stream().forEach(triangle -> {
            // render each line of the triangle to the window.
            Vector2D v0 = window.project(triangle.vertices[0]);
            Vector2D v1 = window.project(triangle.vertices[1]);
            Vector2D v2 = window.project(triangle.vertices[2]);

            this.drawLine(new Line2D(v0, v1));
            this.drawLine(new Line2D(v1, v2));
            this.drawLine(new Line2D(v2, v0));
        });
    }

    private void drawLine(Line2D line) {
        // clip
        Line2D clip = LineClipper.clipLine(line, window);
        // convert to pixels

        // submit pixels to the publisher.

    }

    @Override
    public void onSubscribe(Flow.Subscription subscription) {

    }

    @Override
    public void onNext(Line item) {
        //draw pixels on the camera
        //TODO: implement
    }

    @Override
    public void onError(Throwable throwable) {

    }

    @Override
    public void onComplete() {

    }

    @Override
    public void subscribe(Flow.Subscriber<? super Pixel> subscriber) {
        output.subscribe(subscriber);
    }
}
