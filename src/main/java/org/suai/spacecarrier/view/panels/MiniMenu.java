package org.suai.spacecarrier.view.panels;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MiniMenu {

    public static final int WIDTH   = 140; // ширина окна
    public static final int HEIGHT  = 190; // высота окна

    private static GameState gameState; // состояние игры
    private static boolean created = false; // создано ли окно

    // создание окна мини-меню
    public static void miniMenuLauncher () {
        if (created) {
             return;
        }
        setGameState(GameState.WAIT); // ожидание пользователя

        JFrame miniMenu = new JFrame("Pause"); // создание окна
        miniMenu.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE); // на крестик закрыть нельзя

        Dimension size = new Dimension(WIDTH, HEIGHT); // размеры окна
        miniMenu.setSize(size); // установление размера окна

        miniMenu.setLocationRelativeTo(null); //разместить окно по середине экрана
        miniMenu.setResizable(false); // запрет пользователю менять размер окна

        JPanel miniMenuPanel = new JPanel(); // создание панели с кнопками
        miniMenuPanel.setPreferredSize(size); // размер листа
        miniMenuPanel.setLayout(null); // расположение контента не задано

        // кнопка "Resume"
        JButton resumeButton = new JButton("Resume");
        resumeButton.setBounds(20, 20, 100, 30);
        miniMenuPanel.add(resumeButton);
        resumeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setGameState(GameState.CONTINUE); // продолжение работы
                miniMenu.dispose(); // закрыть текущее окно
            }
        });

        // кнопка "Restart"
        JButton restartButton = new JButton("Restart");
        restartButton.setBounds(20, 60, 100, 30);
        miniMenuPanel.add(restartButton);
        restartButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setGameState(GameState.RESTART); // перезапуск игры
                miniMenu.dispose(); // закрытие текущего окна
            }
        });

        // кнопка "Help"
        JButton helpButton = new JButton("Help");
        helpButton.setBounds(20, 100, 100, 30);
        miniMenuPanel.add(helpButton);
        helpButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(miniMenu, "Стрелки - движение:\nUP - вверх \nDOWN - " +
                                "вниз \nLEFT - влево \nRIGHT - вправо \nSPACE (Editor) - поставить/удалить " +
                                "препятствие\nESCAPE - открыть мини-меню в игре", "Help",
                        JOptionPane.PLAIN_MESSAGE);
            }
        });

        // кнопка Quit
        JButton quitButton = new JButton("Quit");
        quitButton.setBounds(20, 140, 100, 30);
        miniMenuPanel.add(quitButton);
        quitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setGameState(GameState.QUIT); // выход из игры
                miniMenu.dispose();
            }
        });

        miniMenu.add(miniMenuPanel); // добавить панель с кнопками в окно
        miniMenu.pack(); // укладка всего

        miniMenu.setVisible(true); // сделать видимым для пользователя

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
