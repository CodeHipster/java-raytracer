package oostd.am.raytracer.visualize.desktop.render;

import oostd.am.raytracer.api.camera.Pixel;
import oostd.am.raytracer.api.camera.Resolution;

import javax.swing.JFrame;
import java.awt.Dimension;
import java.awt.DisplayMode;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Flow;

/**
 * Creates and manages windows for pixel output
 */
public class ScreenManager implements PixelSubscriberFactory {
    private List<JFrame> frames;
    private Rectangle screen;

    public ScreenManager() {
        this.frames = new ArrayList<>();
        DisplayMode displayMode = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDisplayMode();
        screen = new Rectangle(0,0, displayMode.getWidth(), displayMode.getHeight());
    }

    @Override
    public Flow.Subscriber<Pixel> createSubscriber(Resolution resolution) {
        RenderFrame renderFrame = new RenderFrame(convert(resolution));
        placeFrame(renderFrame);
        frames.add(renderFrame);
        return renderFrame.getPixelConsumer();
    }

    private void placeFrame(JFrame frame){
        if(frames.isEmpty()) {
            frame.setLocation(0, 0);
            return;
        }

        // Get previous screen
        JFrame previousFrame = frames.get(frames.size() - 1);
        Rectangle previousBounds = previousFrame.getBounds();

        if(placeNextTo(frame, previousBounds)) return;
        else if(placeBelow(frame, previousBounds)) return;
        else{ // place on the left top again
            frame.setLocation(0,0);
        }
    }

    private boolean placeNextTo(JFrame frame, Rectangle rectangle){
        if(screen.contains(rectangle.getMaxX()+ frame.getWidth(), rectangle.getY())){
            frame.setLocation((int)rectangle.getMaxX(), (int)rectangle.getY());
            return true;
        }
        return false;
    }

    private boolean placeBelow(JFrame frame, Rectangle rectangle){
        if(screen.contains(rectangle.getX(), rectangle.getMaxY() + frame.getHeight())){
            frame.setLocation((int)rectangle.getX(), (int)rectangle.getMaxY());
            return true;
        }
        return false;
    }

    private Dimension convert(Resolution resolution){
        return new Dimension(resolution.width, resolution.height);
    }
}
