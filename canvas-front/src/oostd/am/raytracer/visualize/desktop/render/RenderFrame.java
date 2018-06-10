package oostd.am.raytracer.visualize.desktop.render;

import oostd.am.raytracer.api.camera.Pixel;

import javax.swing.*;
import java.awt.*;

public class RenderFrame extends JFrame {
    private Canvas canvas;

    public RenderFrame(int width, int height){
        super("Trace window");
        canvas = new Canvas();
        this.add(canvas);

        this.setSize(width, height);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }

    public void drawPixel(Pixel pixel){

        System.out.println("drawing pixel");
        Graphics graphics = canvas.getGraphics();
        Color color = new Color(pixel.color.r,pixel.color.g,pixel.color.b);
        graphics.setColor(color);
        graphics.drawLine(
                pixel.position.x,
                pixel.position.y,
                pixel.position.x,
                pixel.position.y);
    }
}
