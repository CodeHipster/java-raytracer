package oostd.am.raytracer.visualize.desktop.render;

import oostd.am.raytracer.api.debug.DebugLine;
import oostd.am.raytracer.api.geography.UnitVector;
import oostd.am.raytracer.api.geography.Vector;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.concurrent.Flow;

public class RightDebugPanel extends JPanel implements Flow.Subscriber<DebugLine> {

    private UnitVector planeNormal;

    private volatile BufferedImage imageBuffer;
    private Flow.Subscription subscription;

    public RightDebugPanel(UnitVector planeNormal){
        //perspective? plane to project on?
        this.planeNormal = planeNormal;
        this.imageBuffer = new BufferedImage(500,500,BufferedImage.TYPE_3BYTE_BGR);
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(500,500);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics graphics = g.create();
        graphics.drawImage(imageBuffer, 0, 0, null);
        graphics.dispose();
    }

    @Override
    public void onSubscribe(Flow.Subscription subscription) {
        System.out.println("subscribed to subcription: " + subscription);
        this.subscription = subscription;
        subscription.request(1);
    }

    @Override
    public void onNext(DebugLine line) {
        //TODO: calculate where on the u,v, the line should be drawn. (collision point with plane)
        System.out.println("received debugline");
        //project the 2 points onto the plane
//        Vector u1 = line.from;
//        Vector u2 = line.to;
//        Vector n = planeNormal;
//        Vector projection1 = u1.subtract(n.scale(u1.dot(n)));
//        Vector projection2 = u2.subtract(n.scale(u2.dot(n)));

        Graphics2D graphics = imageBuffer.createGraphics();
        graphics.setColor(Color.WHITE);
        int x1 = (int) line.from.z * 20;
        int y1 = 499 - (int) line.from.y * 20;
        int x2 = (int) line.to.z * 20;
        int y2 = 499 - (int) line.to.y * 20;
        graphics.drawLine(x1, y1, x2, y2);
        graphics.dispose();
        subscription.request(1);
    }

    @Override
    public void onError(Throwable throwable) {
        System.out.println("debug panel reports error: " + throwable);

    }

    @Override
    public void onComplete() {
        System.out.println("debug panel listener completed");

    }
}
