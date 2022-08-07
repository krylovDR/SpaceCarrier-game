package org.suai.spacecarrier.model.game;

import org.suai.spacecarrier.model.level.Level;
import org.suai.spacecarrier.model.level.TileType;
import org.suai.spacecarrier.view.panels.GameState;

import java.util.ArrayList;

public class CollisionDetector {

    private static GameState gameState = GameState.CONTINUE; // текущее состояние данной игры

    // определить коллизию
    public static boolean detectCollision (Player player, Level level, Laser laser) {
        int[][] tileMap = level.getTileMap(); // копируем массив с уровнем

        // вычисление номеров клеток, в которых находится корабль игрока
        int y1 = (int)(player.x / Level.TILE_SCALE);
        int x1 = (int)(player.y / Level.TILE_SCALE);
        int y2 = (int)((player.x + Player.SPRITE_SCALE) / Level.TILE_SCALE);
        int x2 = (int)((player.y + Player.SPRITE_SCALE) / Level.TILE_SCALE);

        // проверка на победу
        if (tileMap[x1][y1] == TileType.BASE.numeric()) {
            gameState = GameState.WIN; // игрок одержал победу
            return true; // произошла коллизия
        }

        // обнаружение коллизии для каждого угла корабля
        if (tileMap[x1][y1] == TileType.COIN.numeric()) {
            Coin.addCoin(); // добавить монетку игроку
            level.removeCoinAt(x1, y1); // удалить монетку из карты уровня
            gameState = GameState.CONTINUE;
            return false; // коллизии не было
        }
        if (tileMap[x1][y2] == TileType.COIN.numeric()) {
            Coin.addCoin();
            level.removeCoinAt(x1, y2);
            gameState = GameState.CONTINUE;
            return false;
        }
        if (tileMap[x2][y1] == TileType.COIN.numeric()) {
            Coin.addCoin();
            level.removeCoinAt(x2, y1);
            gameState = GameState.CONTINUE;
            return false;
        }
        if (tileMap[x2][y2] == TileType.COIN.numeric()) {
            Coin.addCoin();
            level.removeCoinAt(x2, y2);
            gameState = GameState.CONTINUE;
            return false;
        }

        // обнаружение коллизии для каждого угла корабля
        if (tileMap[x1][y1] != TileType.EMPTY.numeric() &&
                tileMap[x1][y1] != TileType.BASE.numeric()) {
            gameState = GameState.LOSE; // игрок проиграл
            return true; // коллизия произошла
        }
        if (tileMap[x1][y2] != TileType.EMPTY.numeric() &&
                tileMap[x1][y2] != TileType.BASE.numeric()) {
            gameState = GameState.LOSE;
            return true;
        }
        if (tileMap[x2][y1] != TileType.EMPTY.numeric() &&
                tileMap[x2][y1] != TileType.BASE.numeric()) {
            gameState = GameState.LOSE;
            return true;
        }
        if (tileMap[x2][y2] != TileType.EMPTY.numeric() &&
                tileMap[x2][y2] != TileType.BASE.numeric()) {
            gameState = GameState.LOSE;
            return true;
        }

        // проверка на уничтожение корабля лазером
        ArrayList<Laser.LaserState> lasers = new ArrayList<>(laser.getLasers()); // копия лазеров

        for (int i = 0; i < lasers.size(); i++) {
            // проверка для одного конца лазера
            if (lasers.get(i).getNowX() > player.x &&
                    lasers.get(i).getNowX() < player.x + Player.SPRITE_SCALE &&
                    lasers.get(i).getNowY() > player.y &&
                    lasers.get(i).getNowY() < player.y + Player.SPRITE_SCALE) {
                gameState = GameState.LOSE; // игрок проиграл
                return true;
            }
            // проверка для второго конца лазера
            if (lasers.get(i).getNowX() + Laser.SCALE_LENGTH > player.x &&
                    lasers.get(i).getNowX() + Laser.SCALE_LENGTH < player.x + Player.SPRITE_SCALE &&
                    lasers.get(i).getNowY() + Laser.SCALE_LENGTH > player.y &&
                    lasers.get(i).getNowY() + Laser.SCALE_LENGTH < player.y + Player.SPRITE_SCALE) {
                gameState = GameState.LOSE;
                return true;
            }
        }

        gameState = GameState.CONTINUE;
        return false; // коллизий не было
    }

    // определить столкновения лазера с элементами карты
    public static void detectCollisionLaser (Laser.LaserState laser, Level level) {
        int numberTileWidth; // номер клетки карты по ширине
        int numberTileHeight; // номер клетки карты по высоте
        int[][] tileMap = level.getTileMap();

        // относительно каждого направления движения определяется, соответствующая возможная коллизия
        switch (laser.getLaserVector()) {
            case NORTH:
                numberTileWidth = laser.getNowX() / Level.TILE_SCALE; // текущая ячейка, где находится лазер (по ширине)
                numberTileHeight = laser.getNowY() / Level.TILE_SCALE; // по высоте
                // если есть препятствие, то новый выстрел
                if (tileMap[numberTileHeight][numberTileWidth] != TileType.EMPTY.numeric() &&
                        tileMap[numberTileHeight][numberTileWidth] != TileType.COIN.numeric()) {
                    laser.setNowY(laser.getStartY()); // обновление координаты лазера на место выстрела
                }
                return;
            case SOUTH:
                numberTileWidth = laser.getNowX() / Level.TILE_SCALE;
                numberTileHeight = (laser.getNowY() + Laser.SCALE_LENGTH) / Level.TILE_SCALE;
                if (tileMap[numberTileHeight][numberTileWidth] != TileType.EMPTY.numeric() &&
                        tileMap[numberTileHeight][numberTileWidth] != TileType.COIN.numeric()) {
                    laser.setNowY(laser.getStartY());
                }
                return;
            case EAST:
                numberTileWidth = laser.getNowX() / Level.TILE_SCALE;
                numberTileHeight = laser.getNowY() / Level.TILE_SCALE;
                if (tileMap[numberTileHeight][numberTileWidth] != TileType.EMPTY.numeric() &&
                        tileMap[numberTileHeight][numberTileWidth] != TileType.COIN.numeric()) {
                    laser.setNowX(laser.getStartX());
                }
                return;
            case WEST:
                numberTileWidth = (laser.getNowX() + Laser.SCALE_LENGTH) / Level.TILE_SCALE;
                numberTileHeight = laser.getNowY() / Level.TILE_SCALE;
                if (tileMap[numberTileHeight][numberTileWidth] != TileType.EMPTY.numeric() &&
                        tileMap[numberTileHeight][numberTileWidth] != TileType.COIN.numeric()) {
                    laser.setNowX(laser.getStartX());
                }
                return;
            default:
                break;
        }
    }

    public static GameState getGameState () {
        return gameState;
    }

    public static void restartGameState () {
        gameState = GameState.CONTINUE;
    }
}
