package org.suai.spacecarrier.model.editor;

import org.junit.Test;
import org.suai.spacecarrier.model.io.EditorInput;

import static org.junit.Assert.*;

public class EditLevelTest {

    @Test
    public void getTileMap() {
        EditorInput input = new EditorInput("levelTest.lvl");
        int[][] actual = input.getLevel().getTileMap();
        int[][] expected = {{0,0,0,0,1,1,1,1,0,0,1,0,0,0,0,1},
                            {0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0},
                            {1,1,0,0,1,1,1,1,0,0,0,0,1,0,0,0},
                            {0,0,0,0,1,0,0,0,0,0,0,0,1,0,0,0},
                            {0,0,0,0,1,1,1,1,0,0,0,0,0,0,0,8},
                            {0,7,0,0,1,0,0,0,0,0,1,0,0,0,0,8},
                            {0,0,0,0,1,1,1,1,0,0,1,0,0,0,0,8},
                            {0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,8},
                            {0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0},
                            {0,1,0,0,0,1,0,0,0,0,1,1,0,1,0,1},
                            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
                            {1,0,0,0,1,0,0,0,1,0,0,0,0,0,0,0}};
        assertArrayEquals(expected, actual);
    }
}