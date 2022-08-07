package org.suai.spacecarrier.view.graphics;

import java.awt.*;

public class Background {

    private Texture bg; // фон

    public Background (String fileName) {
        bg = new Texture(fileName);
    }

    public void render (Graphics2D g) {
        bg.render(g, 0, 0);
    }
}
