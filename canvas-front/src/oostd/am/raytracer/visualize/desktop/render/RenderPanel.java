package oostd.am.raytracer.visualize.desktop.render;

import oostd.am.raytracer.api.camera.Pixel;
import oostd.am.raytracer.api.camera.PixelSubscriber;
import oostd.am.raytracer.api.camera.Resolution;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.concurrent.Flow;

/**
 * Panel that listens to pixels and draws them on screen with given interval.
 */
public class RenderPanel extends JPanel implements PixelSubscriber {

    private final BufferedImage imageBuffer;
    private final Resolution resolution;
    private long lastRepaint;
    private final long repaintInterval;

    /**
     * @param resolution,  the size of the panel.
     * @param refreshRate, amount of repaints per second.
     */
    public RenderPanel(Resolution resolution, int refreshRate, String name) {
        this.imageBuffer = new BufferedImage(resolution.width, resolution.height, BufferedImage.TYPE_3BYTE_BGR);
        this.resolution = resolution;
        this.lastRepaint = System.currentTimeMillis();
        this.repaintInterval = 1000 / refreshRate;
        this.setName(name);
    }

    public Dimension getPreferredSize() {
        return new Dimension(imageBuffer.getWidth(), imageBuffer.getHeight());
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics graphics = g.create();
        graphics.drawImage(imageBuffer, 0, 0, null);
        graphics.dispose();
    }

    @Override
    public void onSubscribe(Flow.Subscription subscription) {
        subscription.request(Long.MAX_VALUE);
        System.out.println("Render panel subscribed: " + subscription);
    }

    @Override
    public void onNext(Pixel pixel) {
        int x = pixel.position.x;
        //in screen space y goes down, in scene space it goes up.
        int y = imageBuffer.getHeight() - 1 - pixel.position.y;

        if (x < imageBuffer.getWidth() && y < imageBuffer.getHeight() &&
                x >= 0 && y >= 0) {
            RGBColor rgbColor = new RGBColor(imageBuffer.getRGB(x, y));
            rgbColor.add(pixel.color.r, pixel.color.g, pixel.color.b);
            imageBuffer.setRGB(x, y, rgbColor.asInt());
        }
        this.repaint();
    }

    @Override
    public void onError(Throwable throwable) {
        System.out.println("subscriber error occured: " + throwable);
        throw new RuntimeException(throwable);
    }

    @Override
    public void onComplete() {
        super.repaint();
        System.out.println("subscriber completed: " + this.getName());
    }

    @Override
    public void repaint() {
        long now = System.currentTimeMillis();
        if (lastRepaint + repaintInterval < now) {
            super.repaint();
            lastRepaint = now;
        }
    }

    @Override
    public Resolution getResolution() {
        return resolution;
    }
}
