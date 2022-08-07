package org.suai.spacecarrier.model.utils;

public class RecordsTable {

    public static final int     COUNT_LEADERS   = 3; // количество лидеров в таблице рекордов

    // проверка попадания в лидеры
    public static boolean confirmLeader (int challengerCoins, String fileName) {
        Leader[] leaders = ResourceLoader.importRecords(fileName); // загружаем данные из файла

        // проверка по количеству монет
        for (int i = 0; i < COUNT_LEADERS; i++) {
            if (challengerCoins > leaders[i].getCoins()) {
                return true; // игрок попал в лидеры
            }
        }

        return false; // игрок не попал в лидеры
    }

    // добавить новый рекорд в таблицу
    public static void addLeader (String challengerName, int challengerCoins) {
        Leader[] leaders = ResourceLoader.importRecords(ResourceLoader.RECORDS_TABLE); // загрузка данных из файла

        for (int i = 0; i < COUNT_LEADERS; i++) {
            // поиск нужной позиции
            if (challengerCoins > leaders[i].getCoins()) {
                swapLeaders(leaders, challengerName, challengerCoins, i); // вставка рекорда в таблицу со сдвигом
                break;
            }
        }

        ResourceLoader.exportRecords(leaders, ResourceLoader.RECORDS_TABLE); // запись новой таблицы в файл
    }

    // вставка рекорда в таблицу на нужную позицию со сдвигом остальных лидеров
    private static void swapLeaders (Leader[] leaders, String challengerName, int challengerCoins, int position) {
        int i = COUNT_LEADERS - 1; // последнее место в таблице рекордов

        // пока не дошли до нужной позиции
        while (i >= position) {
            // если на нужной позиции, то вставить нового лидера
            if (i == position) {
                leaders[i].setName(challengerName); // записать имя
                leaders[i].setCoins(challengerCoins); // записать количество монет
                return;
            }
            leaders[i].setName(leaders[i - 1].getName()); // сместить имя на строчку вниз
            leaders[i].setCoins(leaders[i - 1].getCoins()); // сместить количество монет на строчку вниз

            i--;
        }
    }

    public static class Leader {
        private String name; // имя
        private int coins; // количество монет

        // конструктор
        public Leader () {
            name = "Name";
            coins = 0;
        }

        // сеттер для имени
        public void setName (String name) {
            this.name = name;
        }

        // сеттер для монет
        public void setCoins (int coins) {
            this.coins = coins;
        }

        // геттер для имени
        public String getName () {
            return name;
        }

        // геттер для монет
        public int getCoins () {
            return coins;
        }
    }
}
