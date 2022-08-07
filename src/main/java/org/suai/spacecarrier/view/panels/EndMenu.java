package org.suai.spacecarrier.view.panels;

import org.suai.spacecarrier.model.game.Coin;
import org.suai.spacecarrier.model.utils.RecordsTable;
import org.suai.spacecarrier.model.utils.ResourceLoader;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EndMenu {

    public static final int WIDTH   = 140; // ширина окна
    public static final int HEIGHT  = 150; // высота окна

    private static GameState gameState; // состояние игры
    private static boolean created = false; // создано ли окно

    // создание окна мини-меню
    public static void endMenuLauncher (GameState result) {
        if (created) {
            return;
        }
        setGameState(GameState.WAIT); // ожидание пользователя

        JFrame endMenu = new JFrame("Pause"); // создание окна
        endMenu.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE); // на крестик закрыть нельзя

        // при проигрыше вывести на экран сообщение о поражении
        if (result == GameState.LOSE) {
            JOptionPane.showMessageDialog(endMenu, "ВЫ ПРОИГРАЛИ!" ,"Defeat", JOptionPane.PLAIN_MESSAGE);
        }

        // при победе вывести  на экран сообщение о выигрыше
        if (result == GameState.WIN) {
            JOptionPane.showMessageDialog(endMenu, "ВЫ ВЫИГРАЛИ!" ,"Winner", JOptionPane.PLAIN_MESSAGE);

            // если игрок попал в лидеры
            if (RecordsTable.confirmLeader(Coin.getCoins(), ResourceLoader.RECORDS_TABLE)) {
                // диалоговое окно с просьбой ввести имя для записи в таблицу
                String winnerName = JOptionPane.showInputDialog(endMenu, "<html><h2>Новый рекорд!\n" +
                        "Введите своё имя:");
                RecordsTable.addLeader(winnerName,Coin.getCoins()); // добавление новой записи в таблицу рекордов
            }
        }

        Dimension size = new Dimension(WIDTH, HEIGHT); // размеры окна
        endMenu.setSize(size); // установление размера окна

        endMenu.setLocationRelativeTo(null); //разместить окно по середине экрана
        endMenu.setResizable(false); // запрет пользователю менять размер окна

        JPanel endMenuPanel = new JPanel(); // создание панели с кнопками
        endMenuPanel.setPreferredSize(size); // размер листа
        endMenuPanel.setLayout(null); // расположение контента не задано

        // кнопка "Restart"
        JButton restartButton = new JButton("Restart");
        restartButton.setBounds(20, 20, 100, 30);
        endMenuPanel.add(restartButton);
        restartButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setGameState(GameState.RESTART); // перезапуск игры
                endMenu.dispose(); // закрытие текущего окна
            }
        });

        // кнопка "Help"
        JButton helpButton = new JButton("Help");
        helpButton.setBounds(20, 60, 100, 30);
        endMenuPanel.add(helpButton);
        helpButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(endMenu, "Стрелки - движение:\nUP - вверх \nDOWN - " +
                                "вниз \nLEFT - влево \nRIGHT - вправо \nSPACE (Editor) - поставить/удалить " +
                                "препятствие\nESCAPE - открыть мини-меню в игре", "Help",
                        JOptionPane.PLAIN_MESSAGE);
            }
        });

        // кнопка Quit
        JButton quitButton = new JButton("Quit");
        quitButton.setBounds(20, 100, 100, 30);
        endMenuPanel.add(quitButton);
        quitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setGameState(GameState.QUIT); // выход из игры
                endMenu.dispose();
            }
        });

        endMenu.add(endMenuPanel); // добавить панель с кнопками в окно
        endMenu.pack(); // укладка всего

        endMenu.setVisible(true); // сделать видимым для пользователя

        created = true; // окно было создано


    }

    // сеттер для состояния игры
    private static void setGameState (GameState gs) {
        gameState = gs;
    }

    // геттер для состояния игры
    public static GameState getGameState () {
        return gameState;
    }

    // для Game для закрытия окна
    public static void setCreated (boolean choice) {
        created = choice;
    }
}
