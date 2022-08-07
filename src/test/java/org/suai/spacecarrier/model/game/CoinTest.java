package org.suai.spacecarrier.model.game;

import org.junit.Test;

import static org.junit.Assert.*;

public class CoinTest {

    @Test
    public void getCoins() {
        int actual = Coin.getCoins();
        int expected = 0;
        assertEquals(expected, actual);
    }
}