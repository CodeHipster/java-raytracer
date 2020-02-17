package oostd.am.raytracer.debug;

import oostd.am.raytracer.api.camera.Pixel;
import oostd.am.raytracer.api.camera.Resolution;
import oostd.am.raytracer.api.debug.ColoredLine2D;
import oostd.am.raytracer.api.debug.Line;
import oostd.am.raytracer.api.debug.Line2D;
import oostd.am.raytracer.api.debug.Window;
import oostd.am.raytracer.api.geography.PixelPosition;
import oostd.am.raytracer.api.geography.Vector2D;

import java.util.List;
import java.util.concurrent.Flow;
import java.util.concurrent.SubmissionPublisher;

/**
 * DebugWindow wraps a DebugCamera to translate from lines to pixels.
 */
public class DebugLineProcessor implements Flow.Processor<Line, Pixel> {

    private Resolution resolution;
    private Window window;
    private SubmissionPublisher<Pixel> output;
    private Flow.Subscription subscription;

    public DebugLineProcessor(Window window, Resolution resolution) {
        this.resolution = resolution;
        this.window = window;
        this.output = new SubmissionPublisher<>();
    }

    @Override
    public void onSubscribe(Flow.Subscription subscription) {
        this.subscription = subscription;
        subscription.request(1);
        System.out.println("Line processor subscribed: " + subscription);
    }

    @Override
    public void onNext(Line line) {
        // clip
        Vector2D from = window.project(line.from);
        Vector2D to = window.project(line.to);

        ColoredLine2D colored = new ColoredLine2D(from, to, line.color);
        boolean clipped = LineClipper.clipLine(colored, window);

        // convert to pixels
        if(clipped){
            ColoredLine2D screenLine = convertToScreenSize(colored);
            drawLine(screenLine);
        }
        subscription.request(1);
    }

    private void drawLine(ColoredLine2D line){
        // what algorithm to use?
        List<PixelPosition> draw = LineDrawer.draw(line);
        draw.forEach(p -> output.submit(new Pixel(p, line.color)));
    }

    private <T extends Line2D> T convertToScreenSize(T line){
        //line is plotted on a window with an origin in the middle.
        // screen has 0,0 on the left bottom
        line.addSelf(new Vector2D(window.dimension.width / 2, window.dimension.height / 2));
        double hScale = resolution.width / window.dimension.width;
        double vScale = resolution.height / window.dimension.height;

        line.from.scaleSelf(hScale, vScale);
        line.to.scaleSelf(hScale, vScale);
        return line;
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
