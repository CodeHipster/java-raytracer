package oostd.am.raytracer.visualize.desktop.render;

import oostd.am.raytracer.api.camera.Pixel;

import javax.swing.*;
import java.awt.*;
import java.util.concurrent.Flow;

public class RenderFrame extends JFrame {

    private RenderPanel renderPanel;

    public RenderFrame(Dimension dimension){
        super("Trace window");
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);//TODO: register a WindowListener to unsubscribe from the pixel output.
        this.setVisible(true);
        renderPanel = new RenderPanel(dimension, 10);
        this.add(renderPanel);
        this.pack();
    }

    public Flow.Subscriber<Pixel> getPixelConsumer(){
        return renderPanel;
    }
}
