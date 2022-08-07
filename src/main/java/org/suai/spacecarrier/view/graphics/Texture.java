package org.suai.spacecarrier.view.graphics;

import org.suai.spacecarrier.model.utils.ResourceLoader;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Texture {

    BufferedImage image; // изображение

    // конструктор
    public Texture (String imageName) {
        image = ResourceLoader.loadImage(imageName); // загружаем изображение из resources
    }

    // получить изображение
    public BufferedImage getImage () {
        return image;
    }

    // вывод на экран изображения текстуры
    public void render (Graphics2D g, float x, float y) {
        g.drawImage(image, (int)(x), (int)(y),
                (int)(image.getWidth()), (int)(image.getHeight()), null);
    }
}
