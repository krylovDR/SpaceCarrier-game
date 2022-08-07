package org.suai.spacecarrier.model.utils;

import org.junit.Test;

import static org.junit.Assert.*;

public class RecordsTableTest {

    @Test
    public void confirmLeader() {
        boolean actual = RecordsTable.confirmLeader(7, "records_tableTest.txt");
        assertTrue(actual);

        actual = RecordsTable.confirmLeader(1, "records_tableTest.txt");
        assertFalse(actual);
    }
}