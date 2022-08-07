package org.suai.spacecarrier.model.game;

import org.junit.Test;
import org.suai.spacecarrier.model.level.Level;
import org.suai.spacecarrier.view.panels.GameState;

import static org.junit.Assert.*;

public class CollisionDetectorTest {

    @Test
    public void detectCollision() {
        Level lvl = new Level("levelTest.lvl");
        Player player = new Player(1,6 * Level.TILE_SCALE);
        Laser laser = new Laser(lvl);
        boolean actual = CollisionDetector.detectCollision(player, lvl, laser);
        assertFalse(actual);
        // лазер не долетит до игрока

        Player player2 = new Player(1, 2 * Level.TILE_SCALE);
        actual = CollisionDetector.detectCollision(player2, lvl, laser);
        assertTrue(actual);


        Player player3 = new Player(15 * Level.TILE_SCALE + 10, 6 * Level.TILE_SCALE);
        actual = CollisionDetector.detectCollision(player3, lvl, laser);
        assertTrue(actual);
    }

    @Test
    public void getGameState() {
        Player player = new Player(15 * Level.TILE_SCALE + 10, 6 * Level.TILE_SCALE);
        Level lvl = new Level("levelTest.lvl");
        Laser laser = new Laser(lvl);
        CollisionDetector.detectCollision(player, lvl, laser);

        GameState actual = CollisionDetector.getGameState();
        GameState expected = GameState.WIN;
        assertEquals(expected, actual);

        Player player2 = new Player(1, 2 * Level.TILE_SCALE);
        CollisionDetector.detectCollision(player2, lvl, laser);

        actual = CollisionDetector.getGameState();
        expected = GameState.LOSE;
        assertEquals(expected, actual);

        Player player3 = new Player(1, 6 * Level.TILE_SCALE);
        CollisionDetector.detectCollision(player3, lvl, laser);

        actual = CollisionDetector.getGameState();
        expected = GameState.CONTINUE;
        assertEquals(expected, actual);
    }
}