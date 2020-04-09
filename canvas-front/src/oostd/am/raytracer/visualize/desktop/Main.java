package oostd.am.raytracer.visualize.desktop;

import oostd.am.raytracer.api.camera.Resolution;
import oostd.am.raytracer.visualize.desktop.menu.MenuFrame;
import oostd.am.raytracer.visualize.desktop.render.ScreenManager;

import javax.swing.*;

public class Main {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            System.out.println("Created Menu on EDT? " + SwingUtilities.isEventDispatchThread());
            ScreenManager screenManager = new ScreenManager(
                    new Resolution(800, 800),
                    new Resolution(800, 800));
            new MenuFrame(screenManager);
        });
    }
}
