package org.suai.spacecarrier.view.panels;

import org.suai.spacecarrier.model.editor.Editor;
import org.suai.spacecarrier.model.game.Game;
import org.suai.spacecarrier.model.utils.RecordsTable;
import org.suai.spacecarrier.model.utils.ResourceLoader;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainMenu {

    public static final int WIDTH   = 800; // ширина окна
    public static final int HEIGHT  = 600; // высота окна

    private static JFrame startMenu; // окно
    private static boolean created = false; // создано ли окно

    // создание Main Menu
    public static void create () {
        if (created) {
            return; //если окно создано - ничего не делать
        }

        JFrame startMenu = new JFrame("SpaceCarrier"); // окно с именем "SpaceCarrier"
        startMenu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //закрыть программу на "крестик"

        Dimension size = new Dimension(WIDTH, HEIGHT); // объект с размером листа
        startMenu.setSize(size); // установили размер окна

        startMenu.setLocationRelativeTo(null); //разместить окно по середине экрана
        startMenu.setResizable(false); // запрет пользователю менять размер окна

        MainMenuPanel panel = new MainMenuPanel(); // создание панели с кнопками
        panel.setPreferredSize(size); // размер листа
        panel.setLayout(null); // расположение контента не задано

        // создание кнопки "Start Game"
        JButton startButton = new JButton("Start Game"); // создание кнопки
        startButton.setBounds(350, 300, 100, 30); // местоположение и размер
        panel.add(startButton); // добавление кнопки на панель
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startMenu.dispose(); // закрытие видимости окна главного меню
                Game spaceCarrier = new Game(); // создание объекта игры
                spaceCarrier.start(); // запуск игру
            }
        });

        // "Help"
        JButton helpButton = new JButton("Help");
        helpButton.setBounds(350, 340, 100, 30);
        panel.add(helpButton);
        helpButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(startMenu, "Стрелки - движение:\nUP - вверх \nDOWN - вниз \n" +
                        "LEFT - влево \nRIGHT - вправо \nSPACE (Editor) - поставить/удалить препятствие \nESCAPE - " +
                                "открыть мини-меню в игре", "Help",
                                        JOptionPane.PLAIN_MESSAGE);
            }
        });

        // "Records"
        JButton recordsButton = new JButton("Records");
        recordsButton.setBounds(350, 380, 100, 30);
        panel.add(recordsButton);
        recordsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // считывание информации из файла о рекордсменах
                RecordsTable.Leader[] leaders = ResourceLoader.importRecords(ResourceLoader.RECORDS_TABLE);
                StringBuilder message = new StringBuilder(); // для формирования таблицы на экране

                message.append("Пример: №.  Имя   Количество_монет" +
                        "\n--------------------------------------------------\n");
                for (int i = 0; i < RecordsTable.COUNT_LEADERS; i++) {
                    message.append((i + 1) + ".  " + leaders[i].getName() + "      " + leaders[i].getCoins() + "\n");
                }
                // вывод таблицы на экран
                JOptionPane.showMessageDialog(startMenu, message.toString(), "Records Table",
                        JOptionPane.PLAIN_MESSAGE);
            }
        });

        // "Editor"
        JButton editorButton = new JButton("Editor");
        editorButton.setBounds(350, 420, 100, 30);
        panel.add(editorButton);
        editorButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startMenu.dispose();
                Editor levelEditor = new Editor();
                levelEditor.start();
            }
        });

        // "Quit"
        JButton quitButton = new JButton("Quit");
        quitButton.setBounds(350, 460, 100, 30);
        panel.add(quitButton);
        quitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                created = false;
                startMenu.dispose();
            }
        });

        startMenu.add(panel); // добавление панели в окно
        startMenu.pack(); // укладка всего

        created = true; // окно заупстилось

        startMenu.setVisible(true); // делаем окно видимым

    }

}
