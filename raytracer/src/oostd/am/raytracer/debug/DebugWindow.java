package oostd.am.raytracer.debug;

import oostd.am.raytracer.api.camera.Pixel;
import oostd.am.raytracer.api.debug.Line;
import oostd.am.raytracer.api.debug.Line2D;
import oostd.am.raytracer.api.debug.Window;
import oostd.am.raytracer.api.geography.Dimension;
import oostd.am.raytracer.api.geography.UnitVector;
import oostd.am.raytracer.api.geography.Vector;
import oostd.am.raytracer.api.scenery.Scene;

import java.util.concurrent.Flow;
import java.util.concurrent.SubmissionPublisher;

/**
 * DebugWindow wraps a DebugCamera to translate from lines to pixels.
 */
public class DebugWindow extends Window implements Flow.Processor<Line, Pixel>{

    //where to output the pixels
    private SubmissionPublisher<Pixel> output;

    public DebugWindow(Vector origin, Vector normal, UnitVector alignmentVector, Dimension dimension) {
        super(origin, normal, alignmentVector, dimension);
        this.output = new SubmissionPublisher<>();
    }

    /**
     * draw lines for the scene geometry to the camera
     * @param scene
     */
    public void renderSceneGeometry(Scene scene){
        scene.getTriangles().stream().forEach(triangle -> {
            // render each line of the triangle to the window.
        });
    }

    // clamp line to window.
    private Line2D clip(Line2D line){
        return null;
    }

    private void drawLine(){

    }

    @Override
    public void onSubscribe(Flow.Subscription subscription) {

    }

    @Override
    public void onNext(Line item) {
        //draw pixels on the camera
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
