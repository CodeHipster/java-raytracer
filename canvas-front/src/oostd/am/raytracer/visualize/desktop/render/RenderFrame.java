package oostd.am.raytracer.visualize.desktop.render;

import javax.swing.*;
import java.awt.*;

public class RenderFrame extends JFrame {
    private Canvas canvas;

    public RenderFrame(){
        super("Trace window");
        canvas = new Canvas();
        this.add(canvas);

        this.setSize(300, 300);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }
}
