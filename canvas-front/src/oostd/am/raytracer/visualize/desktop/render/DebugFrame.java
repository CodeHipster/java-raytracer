package oostd.am.raytracer.visualize.desktop.render;

import javax.swing.*;

public class DebugFrame extends JFrame {

    public DebugFrame(){
        super("Right debug frame");

        this.setSize(500, 500);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }
}
