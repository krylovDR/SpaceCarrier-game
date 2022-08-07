package org.suai.spacecarrier.model.game;

import org.suai.spacecarrier.model.io.Input;
import org.suai.spacecarrier.model.level.Level;
import org.suai.spacecarrier.view.graphics.Texture;
import org.suai.spacecarrier.view.graphics.TexturePath;

import java.awt.*;
import java.awt.event.KeyEvent;

public class Player extends Entity {

    public static final int     SPRITE_SCALE    = 35; // размер текстуры корабля игрока
    public static final int     POSITION_X      = 1; // начальная позиция игрока на карте (по ширине)
    public static final int     POSITION_Y      = Level.TILE_SCALE * 6; // по высоте
    public static final float   SPEED           = 1.5f; // скорость игрока

    private boolean moveable; // возможность двигаться

    // перечисление возможных направлений
    private enum ShipVector {
        NORTH, SOUTH, WEST, EAST;
    }

    private Texture textureNorth; // текстура North
    private Texture textureSouth; // текстура South
    private Texture textureWest; // текстура West
    private Texture textureEast; // текстура East

    private ShipVector shipVector; // направление движения корабля в данный момент

    // конструктор
    public Player (int x, int y) {
        super(EntityType.Player, x, y);

        moveable = true; // может двигаться
        shipVector = ShipVector.WEST; // направление на восток по умолчанию
        textureNorth = new Texture(TexturePath.PLAYER_SHIP_N);
        textureSouth = new Texture(TexturePath.PLAYER_SHIP_S);
        textureWest = new Texture(TexturePath.PLAYER_SHIP_W);
        textureEast = new Texture(TexturePath.PLAYER_SHIP_E);
    }

    @Override
    public void update (Input input) {
        if(!moveable) {
            return; // если не может двигаться, то ничего не делать
        }

        float newX = x; // чтобы игрок не оказался там,
        float newY = y; // где не следует

        // управление игроком
        if (input.getKey(KeyEvent.VK_UP)) {
            newY -= SPEED;
            shipVector = ShipVector.NORTH; // изменение направления корабля
        }
        if (input.getKey(KeyEvent.VK_DOWN)) {
            newY += SPEED;
            shipVector = ShipVector.SOUTH;
        }
        if (input.getKey(KeyEvent.VK_LEFT)) {
            newX -= SPEED;
            shipVector = ShipVector.EAST;
        }
        if (input.getKey(KeyEvent.VK_RIGHT)) {
            newX += SPEED;
            shipVector = ShipVector.WEST;
        }

        // проверка, в пределах ли экрана
        if (newX < 0) {
            newX = 0;
        } else if (newX >= Game.WIDTH - SPRITE_SCALE - 1) {
            newX = Game.WIDTH - SPRITE_SCALE - 1;
        }

        if (newY < 0) {
            newY = 0;
        } else if (newY >= Game.HEIGHT - SPRITE_SCALE - 1) {
            newY = Game.HEIGHT - SPRITE_SCALE - 1;
        }

        x = newX;
        y = newY;


    }

    @Override
    public void render (Graphics2D g) {
        switch (shipVector) {
            case NORTH:
                textureNorth.render(g, x, y);
                break;
            case SOUTH:
                textureSouth.render(g, x, y);
                break;
            case WEST:
                textureWest.render(g, x, y);
                break;
            case EAST:
                textureEast.render(g, x, y);
                break;
            default:
                break;
        }
    }

    // установить начальное местоположение игрока (для пакета)
    void toStart () {
        super.x = POSITION_X;
        super.y = POSITION_Y;
    }

    // указать может ли двигаться игрок (для пакета)
    void setMoveable (boolean choice) {
        moveable = choice;
    }


}
