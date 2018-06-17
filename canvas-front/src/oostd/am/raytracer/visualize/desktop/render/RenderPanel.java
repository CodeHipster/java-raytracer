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
        //scale colors down to 8bit
        Color c = pixel.color;
        int r = (c.r > 255)? c.r = 255: c.r;
        int g = (c.g > 255)? c.g = 255: c.g;
        int b = (c.b > 255)? c.b = 255: c.b;

        int rgb = (r << 16) + (g << 8) + b;
        System.out.println("received pixel");
        imageBuffer.setRGB(pixel.position.x, pixel.position.y, rgb);
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
