package org.suai.spacecarrier.model.editor;

import org.suai.spacecarrier.model.level.Level;
import org.suai.spacecarrier.model.utils.ResourceLoader;
import org.suai.spacecarrier.view.level.Tile;
import org.suai.spacecarrier.model.level.TileType;
import org.suai.spacecarrier.view.graphics.Texture;
import org.suai.spacecarrier.view.graphics.TexturePath;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class EditLevel {

    private int[][] tileMap; // уровень
    private Map<TileType, Tile> tiles; // карта типов клеток и клеток

    // конструктор
    public EditLevel (String fileName) {
        tiles = new HashMap<TileType, Tile>();
        tiles.put(TileType.STAT_METEOR, new Tile (new Texture(TexturePath.STAT_METEOR).getImage(),
                TileType.STAT_METEOR));
        tiles.put(TileType.BASE, new Tile (new Texture(TexturePath.BASE).getImage(),
                TileType.BASE));
        tiles.put(TileType.EMPTY, new Tile (null, TileType.EMPTY));

        tileMap = new int[Level.TILES_IN_WIDTH][Level.TILES_IN_HEIGHT];
        tileMap = ResourceLoader.levelParser(fileName); // загружаем уровень из файла
    }

    public void render (Graphics2D g) {
        for (int i = 0; i < tileMap.length; i++) {
            for (int j = 0; j < tileMap[i].length; j ++) {
                tiles.get(TileType.fromNumeric(tileMap[i][j])).render(g,
                        j * Level.TILE_SCALE, i * Level.TILE_SCALE);
            }
        }
    }

    // возврщяем копию карту уровня
    public int[][] getTileMap () {
        int[][] arr = new int[Level.TILES_IN_HEIGHT][Level.TILES_IN_WIDTH]; // копия уровня

        // копируем содержимое уровня в его копию
        for (int i = 0; i < tileMap.length; i++) {
            System.arraycopy(tileMap[i], 0, arr[i], 0, tileMap[i].length);
        }

        return arr;
    }

    // добавить\удалить препятствие
    public void swapTile (int numberTileHeight, int numberTileWidth) {
        if (tileMap[numberTileWidth][numberTileHeight] == TileType.EMPTY.numeric()) {
            tileMap[numberTileWidth][numberTileHeight] = TileType.STAT_METEOR.numeric();
        } else if (tileMap[numberTileWidth][numberTileHeight] == TileType.STAT_METEOR.numeric()) {
            tileMap[numberTileWidth][numberTileHeight] = TileType.EMPTY.numeric();
        }

    }

}
