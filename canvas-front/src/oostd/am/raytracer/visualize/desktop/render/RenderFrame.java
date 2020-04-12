package oostd.am.raytracer.visualize.desktop.render;

import oostd.am.raytracer.api.camera.PixelSubscriber;
import oostd.am.raytracer.api.camera.Resolution;

import javax.swing.JFrame;

/**
 * Frame for holding pixels
 */
public class RenderFrame extends JFrame {

    private final RenderPanel renderPanel;

    public RenderFrame(Resolution resolution, String name){
        super(name);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);//TODO: register a WindowListener to unsubscribe from the pixel output.
        this.setVisible(true);
        renderPanel = new RenderPanel(resolution, 30, name);
        this.add(renderPanel);
        this.pack();
    }

    public PixelSubscriber getPixelConsumer(){
        return renderPanel;
    }
}
