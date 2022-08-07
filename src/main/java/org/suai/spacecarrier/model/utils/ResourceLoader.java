package org.suai.spacecarrier.model.utils;

import org.suai.spacecarrier.model.level.Level;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;

public class ResourceLoader {

    public static final String  PATH            = "src/main/resources/"; // путь к ресурсам
    public static final String  RECORDS_TABLE   = "records_table.txt"; // название фала с таблицей рекордов

    // загрузка изображения
    public static BufferedImage loadImage (String fileName) {
        BufferedImage image = null; // изображение

        try {
            image = ImageIO.read(new File(PATH + fileName)); // загрузка изображения
        } catch (IOException e) {
            e.printStackTrace();
        }

        return image; // возвращаем изображение
    }

    // считывание уровня из файла
    public static int[][] levelParser(String fileName) {
        int[][] result = new int[Level.TILES_IN_HEIGHT][Level.TILES_IN_WIDTH]; // массив на выход

        // открываем ресурс в try/catch (чтобы он сам закрывался, в случае исключения)
        try (BufferedReader reader = new BufferedReader(new FileReader(new File(PATH + fileName)))) {
            String line;
            int j = 0;

            // считывание построчно до конца файла
            while ((line = reader.readLine()) != null) {
                String[] tokens = line.split(" "); // массив токенов с числами, пропуская делиметеры (пробелы)

                // запись в двумерный массив int
                for (int i = 0; i < tokens.length; i++){
                    result[j][i] = Integer.parseInt(tokens[i]);
                }
                j++; // переход на следующую строку в массиве int
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }

    // сохранение уровня в файл
    public static void exportLevel (int[][] level, String fileName) {

        // открытие файла, так чтобы он при исключении закрывался сам
        try (FileWriter writer = new FileWriter(PATH + fileName)) {

            StringBuilder result = new StringBuilder(); // оформление уровеня под запись в файл
            int i = 0;
            int j = 1;
            while (i < Level.TILES_IN_HEIGHT) {
                result.append(level[i][0]);
                while (j < Level.TILES_IN_WIDTH) {
                    result.append(" ");
                    result.append(level[i][j]);
                    j++;
                }
                if (i + 1 != Level.TILES_IN_HEIGHT) {
                    result.append("\n");
                }
                j = 1;
                i++;
            }

            writer.write(result.toString()); // запись в файл

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // считывание данных о лидерах из файла
    public static RecordsTable.Leader[] importRecords (String fileName) {
        RecordsTable.Leader[] leaders = new RecordsTable.Leader[RecordsTable.COUNT_LEADERS]; // массив лидеров
        for (int i = 0; i < RecordsTable.COUNT_LEADERS; i++) {
            leaders[i] = new RecordsTable.Leader();
        }

        // открываем ресурс в try/catch (чтобы он сам закрывался, в случае исключения)
        try (BufferedReader reader = new BufferedReader(new FileReader(new File(PATH + fileName)))) {
            String line; // для строки из файла
            int j = 0;

            // считывание построчно до конца файла или до заданного числа лидеров
            while (((line = reader.readLine()) != null) && j < RecordsTable.COUNT_LEADERS * 2) {

                // чётная или нечётная строка
                if (j % 2 == 0) {
                    leaders[j / 2].setName(line);
                } else {
                    leaders[j / 2].setCoins(Integer.parseInt(line));
                }

                j++;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return leaders; // возвращение всех лидеров
    }

    // запись данных о лидерах в файл
    public static void exportRecords (RecordsTable.Leader[] leaders, String fileName) {
        int j = 0;
        try (FileWriter writer = new FileWriter(PATH + fileName)) {
            writer.write(leaders[j].getName() + "\n" + leaders[j].getCoins()); // запись первого лидера
            j++;
            while (j < RecordsTable.COUNT_LEADERS) {
                // построчная запись в файл с разделителем
                writer.write("\n" + leaders[j].getName() + "\n" + leaders[j].getCoins()); // запись остальных
                j++;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
