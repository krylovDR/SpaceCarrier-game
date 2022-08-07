package org.suai.spacecarrier.model.level;

import org.junit.Test;

import static org.junit.Assert.*;

public class TileTypeTest {

    @Test
    public void numeric() {
        int actual = TileType.COIN.numeric();
        int expected = 3;
        assertEquals(expected,actual);
    }

    @Test
    public void fromNumeric() {
        TileType actual = TileType.fromNumeric(8);
        assertEquals(TileType.BASE, actual);

        actual = TileType.fromNumeric(65);
        assertEquals(TileType.EMPTY, actual);

        actual = TileType.fromNumeric(-100);
        assertEquals(TileType.EMPTY, actual);
    }
}