package org.suai.spacecarrier.model.utils;

import org.junit.Test;
import org.suai.spacecarrier.model.level.Level;
import org.suai.spacecarrier.view.graphics.TexturePath;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import static org.junit.Assert.*;

public class ResourceLoaderTest {

    @Test
    public void levelParser() {
        int[][] actual = ResourceLoader.levelParser( "levelTest.lvl");
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

    @Test
    public void importRecords() {
        RecordsTable.Leader[] actual = ResourceLoader.importRecords("records_tableTest.txt");
        RecordsTable.Leader[] expected = new RecordsTable.Leader[3];
        for (int i = 0; i < 3; i++){
            expected[i] = new RecordsTable.Leader();
        }
        expected[0].setName("Steewe");
        expected[0].setCoins(9);
        expected[1].setName("Г0N$с4iK");
        expected[1].setCoins(6);
        expected[2].setName("Скайвокер");
        expected[2].setCoins(5);

        for (int i = 0; i < 3; i++) {
            assertEquals(expected[i].getName(), actual[i].getName());
            assertEquals(expected[i].getCoins(), actual[i].getCoins());
        }
    }
}