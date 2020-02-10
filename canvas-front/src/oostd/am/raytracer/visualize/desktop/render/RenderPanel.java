package oostd.am.raytracer.visualize.desktop.render;

import oostd.am.raytracer.api.camera.Pixel;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.concurrent.Flow;

public class RenderPanel extends JPanel implements Flow.Subscriber<Pixel> {

    private volatile BufferedImage imageBuffer;
    private Flow.Subscription subscription;
    private long lastRepaint;
    private long repaintInterval;

    /**
     *
     * @param dimension, the size of the panel.
     * @param refreshRate, amount of repaints per second.
     */
    public RenderPanel(Dimension dimension, int refreshRate) {
        this.imageBuffer = new BufferedImage(dimension.width, dimension.height, BufferedImage.TYPE_3BYTE_BGR);
        this.lastRepaint = System.currentTimeMillis();
        this.repaintInterval = 1000/refreshRate;
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
        this.subscription = subscription;
        subscription.request(1);
        System.out.println("subscribed: " + subscription);
    }

    @Override
    public void onNext(Pixel pixel) {
        int x = pixel.position.x;
        //in screen space y goes down, in scene space it goes up.
        int y = imageBuffer.getHeight() - 1 - pixel.position.y;

        RGBColor rgbColor = new RGBColor(imageBuffer.getRGB(x, y));
        rgbColor.add(pixel.color.r, pixel.color.g, pixel.color.b);

        imageBuffer.setRGB(x, y, rgbColor.asInt());
        subscription.request(1);

        this.repaint();
    }

    @Override
    public void onError(Throwable throwable) {
        System.out.println("subscriber error occured: " + throwable);
        throw new RuntimeException(throwable);
    }

    @Override
    public void onComplete() {
        System.out.println("subscriber completed");
    }

    @Override
    public void repaint(){
        long now = System.currentTimeMillis();
        if(lastRepaint + repaintInterval < now){
            super.repaint();
            lastRepaint = now;
        }
    }
}
