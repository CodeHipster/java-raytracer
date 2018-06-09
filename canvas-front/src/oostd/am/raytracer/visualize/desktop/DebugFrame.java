package oostd.am.raytracer.visualize.desktop;

import javax.swing.*;
import java.awt.*;

public class DebugFrame extends JFrame {
    private Canvas canvas;

    public DebugFrame(){
        super("Debug window");
        canvas = new Canvas();
        this.add(canvas);

        this.setSize(300, 300);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }
}
