package org.suai.spacecarrier.model.game;

import org.suai.spacecarrier.model.level.Level;
import org.suai.spacecarrier.model.level.TileType;
import org.suai.spacecarrier.view.graphics.Texture;
import org.suai.spacecarrier.view.graphics.TexturePath;

import java.awt.*;
import java.util.ArrayList;

public class Laser {

    public static final float   LASER_SPEED     = 3f; // скорость лазера
    public static final int     SCALE_LENGTH    = 20; // длина спрайта лазера

    private ArrayList<LaserState> lasers; // все лазеры

    // направления полёта лазера
    public enum LaserVector {
        NORTH, WEST, SOUTH, EAST;
    }

    // конструктор
    public Laser (Level level) {
        lasers = new ArrayList<>(); // создание ArrayList для лазеров

        findEnemy(level); // найти врагов и добавить лазеры
    }

    public void update (Level level) {

        // изменение местоположения и проверка на коллизии для каждого лазера
        for (int i = 0; i < lasers.size(); i++) {

            switch (lasers.get(i).laserVector) {
                case NORTH:
                    lasers.get(i).nowY -= LASER_SPEED; // изменение местоположения
                    detectOutOfBounds(lasers.get(i), LaserVector.NORTH); // проверка, чтобы не вылетел за пределы экрана
                    CollisionDetector.detectCollisionLaser(lasers.get(i),level); // проверка на столкновения
                    break;
                case SOUTH:
                    lasers.get(i).nowY += LASER_SPEED;
                    detectOutOfBounds(lasers.get(i), LaserVector.SOUTH);
                    CollisionDetector.detectCollisionLaser(lasers.get(i),level);
                    break;
                case WEST:
                    lasers.get(i).nowX += LASER_SPEED;
                    detectOutOfBounds(lasers.get(i), LaserVector.WEST);
                    CollisionDetector.detectCollisionLaser(lasers.get(i),level);
                    break;
                case EAST:
                    lasers.get(i).nowX -= LASER_SPEED;
                    detectOutOfBounds(lasers.get(i), LaserVector.EAST);
                    CollisionDetector.detectCollisionLaser(lasers.get(i),level);
                    break;
                default:
                    break;
            }
        }

    }

    public void render (Graphics2D g) {
        for (int i = 0; i < lasers.size(); i++) {
            int positionX = lasers.get(i).nowX; // координата x
            int positionY = lasers.get(i).nowY; // координата y
            lasers.get(i).texture.render(g, positionX, positionY); // вывести лазер на экран
        }
    }

    // добавлене лазера
    public void addLaser (int x, int y, LaserVector vector) {
        LaserState laser = new LaserState(); // новый лазер (его состояние)

        laser.startX = x; // начальная координата x
        laser.startY = y; // начальная координата y
        laser.nowX = x; // текущая координата x
        laser.nowY = y; // текущая координата y
        laser.laserVector = vector; // направление движения лазера

        // для вертикального и горизонтального движения разные текстуры
        switch (vector) {
            case NORTH:
            case SOUTH:
                laser.texture = new Texture(TexturePath.LASER_VERT);
                break;
            case EAST:
            case WEST:
                laser.texture = new Texture(TexturePath.LASER_HORIZ);
                break;
            default:
                break;
        }

        lasers.add(laser); // добавить лазер в ArrayList
    }

    // проверка, в пределах ли экрана
    public void detectOutOfBounds (LaserState laser, LaserVector vector) {
        if (vector == LaserVector.NORTH) {
            if (laser.nowY < 0) {
                laser.nowY = laser.startY; // чтобы враг снова выстрелил
            }
            return;
        }
        if (vector == LaserVector.SOUTH) {
            if (laser.nowY >= Game.HEIGHT - SCALE_LENGTH - 1) {
                laser.nowY = laser.startY;
            }
            return;
        }
        if (vector == LaserVector.EAST) {
            if (laser.nowX < 0) {
                laser.nowX = laser.startX;
            }
            return;
        }
        if (vector == LaserVector.WEST) {
            if (laser.nowX >= Game.WIDTH - SCALE_LENGTH - 1) {
                laser.nowX = laser.startX;
            }
        }
    }

    public void findEnemy (Level level) {
        lasers.clear(); // очистка ArrayList от старых лазеров
        int[][] tileMap = level.getTileMap(); // копия уровня

        // рассчёт местоположений лазеров относительно кораблей врагов
        for (int i = 0; i < Level.TILES_IN_HEIGHT; i++) {
            for (int j = 0; j < Level.TILES_IN_WIDTH; j++) {
                if (tileMap[i][j] == TileType.ENEMY_NORTH.numeric()) {
                    addLaser(j * Level.TILE_SCALE + 25, i * Level.TILE_SCALE + 1, LaserVector.NORTH);
                }
                if (tileMap[i][j] == TileType.ENEMY_SOUTH.numeric()) {
                    addLaser(j * Level.TILE_SCALE + 25, (i + 1) * Level.TILE_SCALE - Laser.SCALE_LENGTH - 1,
                            LaserVector.SOUTH);
                }
                if (tileMap[i][j] == TileType.ENEMY_WEST.numeric()) {
                    addLaser((j + 1) * Level.TILE_SCALE - Laser.SCALE_LENGTH - 1, i * Level.TILE_SCALE + 25,
                            LaserVector.WEST);
                }
                if (tileMap[i][j] == TileType.ENEMY_EAST.numeric()) {
                    addLaser(j * Level.TILE_SCALE + 1, i * Level.TILE_SCALE + 25, LaserVector.EAST);
                }
            }
        }
    }

    // геттер для lasers
    public ArrayList<LaserState> getLasers () {
        return new ArrayList<>(lasers); // возвращает копию lasers
    }

    // внутренний класс, характеризующий каждый отдельный лазер
    public class LaserState {
        private int startX; // место отправления лазера (враг), координата x
        private int startY; // координата y
        private int nowX; // местонахождение лазера в данный момент, координата x
        private int nowY; // координата y
        private LaserVector laserVector; // направление полёта лазера
        private Texture texture; // текстура лазера

        // геттер для начальной точки лазера X
        public int getStartX () {
            return startX;
        }

        // геттер для начальной точки лазера Y
        public int getStartY () {
            return startY;
        }

        // гетер текущей точки нахождения лазера X
        public int getNowX () {
            return nowX;
        }

        // геттер текущей точки нахождения лазера Y
        public int getNowY () {
            return nowY;
        }

        // геттер направления движения лазера
        public LaserVector getLaserVector () {
            return laserVector;
        }

        // сеттер для текущего местонахождения лазера X
        public void setNowX (int value) {
            nowX = value;
        }

        // сеттер для текущего местонахождения лазера Y
        public void setNowY (int value) {
            nowY = value;
        }
    }
}
