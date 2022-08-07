package org.suai.spacecarrier.model.editor;

import org.suai.spacecarrier.model.level.Level;
import org.suai.spacecarrier.view.graphics.Texture;
import org.suai.spacecarrier.view.graphics.TexturePath;

import java.awt.*;

public class User {

    public static final int     START_TILE  = 0; // начальное местоположение активной ячейки

    private int numberHeight; // текущий номер ячейки по высоте
    private int numberWidth; // по ширине

    private Texture texture; // текстура активной ячейки

    // конструктор
    public User () {
        // начальные точки активной ячейки
        numberHeight = START_TILE;
        numberWidth = START_TILE;

        texture = new Texture(TexturePath.EDITOR); // загрузка необходимой текстуры
    }

    // сдвинуться вверх
    public void goUp () {
        int newHeight = numberHeight; // чтобы не уйти туда, куда не следует
        newHeight--; // вверх на ячейку
        // проверка, не вышел ли за пределы
        if (newHeight < 0) {
            newHeight = 0;
        }
        numberHeight = newHeight;
    }

    // свдинуться вниз
    public void goDown () {
        int newHeight = numberHeight;
        newHeight++;
        if (newHeight >= Level.TILES_IN_HEIGHT) {
            newHeight = Level.TILES_IN_HEIGHT - 1;
        }
        numberHeight = newHeight;
    }

    // сдвинуться влево
    public void goLeft () {
        int newWidth = numberWidth;
        newWidth--;
        if (newWidth < 0) {
            newWidth = 0;
        }
        numberWidth = newWidth;
    }

    // сдвинуться вправо
    public void goRight () {
        int newWidth = numberWidth;
        newWidth++;
        if (newWidth >= Level.TILES_IN_WIDTH) {
            newWidth = Level.TILES_IN_WIDTH - 1;
        }
        numberWidth = newWidth;
    }

    // добавить\удалить препятствие
    public void goSwap (EditLevel level) {
        level.swapTile(numberWidth, numberHeight);
    }

    public void render (Graphics2D g) {
        texture.render(g, numberWidth * Level.TILE_SCALE, numberHeight * Level.TILE_SCALE);
    }

}
