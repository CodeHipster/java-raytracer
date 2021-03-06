package oostd.am.raytracer.visualize.desktop.menu;

import oostd.am.raytracer.visualize.desktop.RenderService;
import oostd.am.raytracer.visualize.desktop.render.ScreenManager;

import javax.swing.*;

/**
 * Menu to configure and start the ray-tracer
 */
public class MenuFrame extends JFrame {

    public MenuFrame(ScreenManager screenManager){
        super("Menu");

        JButton button = new JButton("Start startRendering");
        button.setVerticalTextPosition(AbstractButton.CENTER);
        button.setHorizontalTextPosition(AbstractButton.LEADING); //aka LEFT, for left-to-right locales
        button.setActionCommand("startRendering");
        button.addActionListener(new MenuListener(new RenderService(screenManager)));

        this.add(button);

        this.setSize(300, 300);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }
}
