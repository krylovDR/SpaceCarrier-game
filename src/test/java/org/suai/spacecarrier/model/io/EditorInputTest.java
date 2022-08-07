package org.suai.spacecarrier.model.io;

import org.junit.Test;
import org.suai.spacecarrier.view.panels.GameState;

import static org.junit.Assert.*;

public class EditorInputTest {

    @Test
    public void getGS() {
        EditorInput input = new EditorInput("levelTest.lvl");
        GameState actual = input.getGS();
        GameState expected = GameState.CONTINUE;
        assertEquals(expected,actual);
    }
}