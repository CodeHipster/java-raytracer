package oostd.am.raytracer.visualize.desktop.render;

import oostd.am.raytracer.api.camera.Color;
import oostd.am.raytracer.api.camera.Pixel;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ImageConsumer;
import java.awt.image.ImageObserver;
import java.awt.image.ImageProducer;
import java.util.concurrent.Flow;

public class RenderPanel extends JPanel implements Flow.Subscriber<Pixel> {

    private volatile BufferedImage imageBuffer;
    private Flow.Subscription subscription;

    public RenderPanel() {
        this.imageBuffer = new BufferedImage(300,300,BufferedImage.TYPE_3BYTE_BGR);
    }

    public Dimension getPreferredSize() {
        return new Dimension(300,300);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        //drain pixelOutput, until we have to wait for the next element
        Graphics graphics = g.create();
        graphics.drawImage(imageBuffer, 0, 0, null);

    }

    @Override
    public void onSubscribe(Flow.Subscription subscription) {
        this.subscription = subscription;
        subscription.request(1);
        System.out.println("subscribed: " + subscription);
    }

    @Override
    public void onNext(Pixel pixel) {
        System.out.println("received pixel");
        //scale colors down to 8bit
        Color c = pixel.color;
        int r = (int)(((c.r > 1)? c.r = 1: c.r) * 255);
        int g = (int)(((c.g > 1)? c.g = 1: c.g) * 255);
        int b = (int)(((c.b > 1)? c.b = 1: c.b) * 255);

        //put the colors in the correct place in the integer.
        int rgb = (r << 16) + (g << 8) + b;
        //TODO something with the camera size and panel size and image size.
        imageBuffer.setRGB(pixel.position.x, 300 - pixel.position.y, rgb);
        subscription.request(1);
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
}
