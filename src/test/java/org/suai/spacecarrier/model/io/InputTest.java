package org.suai.spacecarrier.model.io;

import org.junit.Test;

import java.awt.event.KeyEvent;

import static org.junit.Assert.*;

public class InputTest {

    @Test
    public void getKey() {
        Input input = new Input();
        boolean actual = input.getKey(KeyEvent.VK_DOWN);
        assertFalse(actual);
    }
}