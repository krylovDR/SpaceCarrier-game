package org.suai.spacecarrier.model.level;

public enum TileType {

    // перечисляем содержимое клеток
    EMPTY(0),
    STAT_METEOR(1),
    RAND_METEOR(2),
    COIN(3),
    ENEMY_NORTH(4),
    ENEMY_WEST(5),
    ENEMY_SOUTH(6),
    ENEMY_EAST(7),
    BASE(8);

    private int n; // число для каждой клетки

    // конструктор
    TileType (int n) {
        this.n = n;
    }

    // гетер числа для клетки
    public int numeric () {
        return n;
    }

    // гетер клетки для числа
    public static TileType fromNumeric (int n) {
        switch (n) {
            case 1:
                return STAT_METEOR;
            case 2:
                return RAND_METEOR;
            case 3:
                return COIN;
            case 4:
                return ENEMY_NORTH;
            case 5:
                return ENEMY_WEST;
            case 6:
                return ENEMY_SOUTH;
            case 7:
                return ENEMY_EAST;
            case 8:
                return BASE;
            default:
                return EMPTY;
        }
    }

}
