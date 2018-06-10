package oostd.am.raytracer.visualize.desktop.menu;

import javax.swing.*;

public class MenuFrame extends JFrame {
    private JButton button;

    public MenuFrame(){
        super("Menu");

        button = new JButton("Start render");
        button.setVerticalTextPosition(AbstractButton.CENTER);
        button.setHorizontalTextPosition(AbstractButton.LEADING); //aka LEFT, for left-to-right locales
        button.setActionCommand("render");
        button.addActionListener(new MenuListener());

        this.add(button);

        this.setSize(300, 300);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }
}
