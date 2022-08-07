package org.suai.spacecarrier.view.panels;

import org.suai.spacecarrier.model.utils.ResourceLoader;
import org.suai.spacecarrier.view.graphics.TexturePath;

import javax.swing.*;
import java.awt.*;

public class MainMenuPanel extends JPanel {

    // фоновое изображение в Main Menu
    @Override
    protected void paintComponent (Graphics g) {
        super.paintComponent(g);
        g.drawImage(ResourceLoader.loadImage(TexturePath.MAIN_MENU), 0, 0, null);
    }

}
