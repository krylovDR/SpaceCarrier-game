package org.suai.spacecarrier.model.utils;

public class Time {

    public static final long SECOND = 1000000000l; // количество наносекунд в секунде

    // возвращает текущее время в наносекундах
    public static long get () {
        return System.nanoTime();
    }
}
