package oostd.am.raytracer.debug;

import oostd.am.raytracer.api.camera.Pixel;
import oostd.am.raytracer.api.camera.Resolution;
import oostd.am.raytracer.api.debug.Line;
import oostd.am.raytracer.api.debug.Line2D;
import oostd.am.raytracer.api.geography.Dimension;
import oostd.am.raytracer.api.geography.UnitVector;
import oostd.am.raytracer.api.geography.Vector;
import oostd.am.raytracer.api.geography.Vector2D;
import oostd.am.raytracer.api.scenery.Scene;

import java.util.concurrent.Flow;
import java.util.concurrent.SubmissionPublisher;

/**
 * DebugWindow wraps a DebugCamera to translate from lines to pixels.
 */
public class DebugWindow extends oostd.am.raytracer.api.debug.DebugWindow implements Flow.Processor<Line, Pixel>{

    private Resolution resolution;
    private SubmissionPublisher<Pixel> output;

    public DebugWindow(Vector origin, Vector normal, UnitVector alignmentVector, Dimension dimension, Resolution resolution) {
        super(origin, normal, alignmentVector, dimension);
        this.resolution = resolution;
        this.output = new SubmissionPublisher<>();
    }

    /**
     * draw lines for the scene geometry to the camera
     * @param scene
     */
    public void renderSceneGeometry(Scene scene){
        scene.triangles.stream().forEach(triangle -> {
            // render each line of the triangle to the window.
            Vector2D v0 = this.project(triangle.vertices[0]);
            Vector2D v1 = this.project(triangle.vertices[1]);
            Vector2D v2 = this.project(triangle.vertices[2]);

            this.drawLine(new Line2D(v0, v1));
            this.drawLine(new Line2D(v1, v2));
            this.drawLine(new Line2D(v2, v0));
        });
    }

    private void drawLine(Line2D line){
        // clip
        Line2D clip = LineClipper.clipLine(line);
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
