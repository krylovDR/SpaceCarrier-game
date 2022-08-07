package org.suai.spacecarrier.model.level;

import org.suai.spacecarrier.model.game.Game;
import org.suai.spacecarrier.model.game.Player;
import org.suai.spacecarrier.model.utils.ResourceLoader;
import org.suai.spacecarrier.view.graphics.Texture;
import org.suai.spacecarrier.view.graphics.TexturePath;
import org.suai.spacecarrier.view.level.Tile;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Level {

    public static final int     TILE_SCALE          = 50; // размер одной клетки на карте
    public static final int     TILES_IN_WIDTH      = Game.WIDTH / TILE_SCALE;  // количество клеток
    public static final int     TILES_IN_HEIGHT     = Game.HEIGHT / TILE_SCALE; // в ширину и высоту
    public static final int     MAX_RAND_METEOR     = 5; // максимальное количество случайных метеоров на уровне
    public static final int     MAX_COIN            = 30; // максимальное количество монет на уровне
    public static final int     MAX_ENEMY           = 3; // максимальное количество врагов на уровне

    public static final String  LEVEL_NAME          = "level.lvl"; // название файла уровня

    private int[][] tileMap; // уровень
    private Map<TileType, Tile> tiles; // карта типов клеток и клеток

    // конструктор
    public Level (String fileName) {
        tiles = new HashMap<TileType, Tile>();
        tiles.put(TileType.STAT_METEOR, new Tile (new Texture(TexturePath.STAT_METEOR).getImage(),
                TileType.STAT_METEOR));
        tiles.put(TileType.RAND_METEOR, new Tile (new Texture(TexturePath.RAND_METEOR).getImage(),
                TileType.RAND_METEOR));
        tiles.put(TileType.COIN, new Tile (new Texture(TexturePath.COIN).getImage(),
                TileType.COIN));
        tiles.put(TileType.ENEMY_NORTH, new Tile (new Texture(TexturePath.ENEMY_SHIP_N).getImage(),
                TileType.ENEMY_NORTH));
        tiles.put(TileType.ENEMY_WEST, new Tile (new Texture(TexturePath.ENEMY_SHIP_W).getImage(),
                TileType.ENEMY_WEST));
        tiles.put(TileType.ENEMY_SOUTH, new Tile (new Texture(TexturePath.ENEMY_SHIP_S).getImage(),
                TileType.ENEMY_SOUTH));
        tiles.put(TileType.ENEMY_EAST, new Tile (new Texture(TexturePath.ENEMY_SHIP_E).getImage(),
                TileType.ENEMY_EAST));
        tiles.put(TileType.BASE, new Tile (new Texture(TexturePath.BASE).getImage(),
                TileType.BASE));
        tiles.put(TileType.EMPTY, new Tile (null, TileType.EMPTY));

        tileMap = new int[TILES_IN_WIDTH][TILES_IN_HEIGHT];
        fillRand(fileName); // заполнить случайными астероидами

    }

    public void update () {}

    public void render (Graphics2D g) {
        for (int i = 0; i < tileMap.length; i++) {
            for (int j = 0; j < tileMap[i].length; j ++) {
                Tile tile = tiles.get(TileType.fromNumeric(tileMap[i][j]));
                if (tile.type() == TileType.COIN) {
                    tile.render(g, j * TILE_SCALE + 10, i * TILE_SCALE + 10); // если монетка
                } else {
                    tile.render(g, j * TILE_SCALE, i * TILE_SCALE); // остальное
                }
            }
        }
    }

    // заполнение уровня случайными астероидами
    public void fillRand (String fileName) {
        tileMap = ResourceLoader.levelParser(fileName); // загружаем уровень из файла

        // генерация случайных астероидов
        int count = 0;

        // не больше MAX_RAND_METEOR случайных астероидов на карте
        while (count < MAX_COIN) {

            Random r = new Random(); // создание объекта рандома

            // генерация монет
            int first = r.nextInt(12); // до 12 клеток в высоту
            if (first == (Player.POSITION_Y / Level.TILE_SCALE)) {
                continue; // монета не попадёт под корабль в начале уровня
            }
            int second = r.nextInt(16); // до 16 клеток в ширину

            if (tileMap[first][second] == TileType.EMPTY.numeric()) {
                tileMap[first][second] = TileType.COIN.numeric();
            }

            // генерация астероидов
            if (count < MAX_RAND_METEOR) {

                first = r.nextInt(12); // до 12 клеток в высоту
                if (first == (Player.POSITION_Y / Level.TILE_SCALE)) {
                    continue; // враг не попадёт под корабль в начале уровня
                }
                second = r.nextInt(16); // до 16 клеток в ширину

                if (tileMap[first][second] == TileType.EMPTY.numeric()) {
                    tileMap[first][second] = TileType.RAND_METEOR.numeric();
                }
            }

            // генерация врагов
            if (count < MAX_ENEMY) {
                first = r.nextInt(12);
                if (first == (Player.POSITION_Y / Level.TILE_SCALE)) {
                    continue; // корабль не будет уничтожен в начале уровня
                }
                second = r.nextInt(16);
                int vector = r.nextInt(4); // возможные стороны

                if (tileMap[first][second] == TileType.EMPTY.numeric()) {
                    tileMap[first][second] = TileType.fromNumeric(vector + 4).numeric();
                }
            }


            count++;
        }
    }

    // возврщяем копию карту уровня
    public int[][] getTileMap () {
        int[][] arr = new int[TILES_IN_HEIGHT][TILES_IN_WIDTH]; // копия уровня

        // копируем содержимое уровня в его копию
        for (int i = 0; i < tileMap.length; i++) {
            System.arraycopy(tileMap[i], 0, arr[i], 0, tileMap[i].length);
        }

        return arr;
    }

    // удаление моентки на уровне
    public void removeCoinAt (int row, int column) {
        // проверка, действительно ли это монетка
        if (tileMap[row][column] == TileType.COIN.numeric()) {
            tileMap[row][column] = TileType.EMPTY.numeric();
        }
    }

}
