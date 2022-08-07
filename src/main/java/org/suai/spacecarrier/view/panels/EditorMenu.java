package org.suai.spacecarrier.view.panels;

import org.suai.spacecarrier.model.utils.ResourceLoader;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EditorMenu {

    public static final int WIDTH   = 140; // ширина окна
    public static final int HEIGHT  = 190; // высота окна

    private static boolean created = false; // создано ли окно
    private static GameState gameState; // состояние игры

    // создание окна мини-меню редактора
    public static void editorMenuLauncher () {
        if (created) {
            return;
        }
        setGameState(GameState.WAIT); // ожидание пользователя

        JFrame editorMenu = new JFrame("Editor Menu"); // создание окна
        editorMenu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // на крестик закрыть

        Dimension size = new Dimension(WIDTH, HEIGHT); // размеры окна
        editorMenu.setSize(size); // установление размера окна

        editorMenu.setLocationRelativeTo(null); //разместить окно по середине экрана
        editorMenu.setResizable(false); // запрет пользователю менять размер окна

        JPanel editorMenuPanel = new JPanel(); // создание панели с кнопками
        editorMenuPanel.setPreferredSize(size); // размер листа
        editorMenuPanel.setLayout(null); // расположение контента не задано

        // кнопка "Resume"
        JButton resumeButton = new JButton("Resume");
        resumeButton.setBounds(20, 20, 100, 30);
        editorMenuPanel.add(resumeButton);
        resumeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setGameState(GameState.CONTINUE); // продолжение работы
                editorMenu.dispose(); // закрыть текущее окно
            }
        });

        // кнопка "Save"
        JButton saveButton = new JButton("Save");
        saveButton.setBounds(20, 60, 100, 30);
        editorMenuPanel.add(saveButton);
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setGameState(GameState.SAVE);
                JOptionPane.showMessageDialog(editorMenu, "После закрытия данного окна произойдёт\n" +
                                "сохранение уровня в файл", "Save", JOptionPane.PLAIN_MESSAGE);
                editorMenu.dispose();
            }
        });

        // кнопка "Help"
        JButton helpButton = new JButton("Help");
        helpButton.setBounds(20, 100, 100, 30);
        editorMenuPanel.add(helpButton);
        helpButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(editorMenu, "Стрелки - движение:\nUP - вверх \nDOWN - " +
                                "вниз \nLEFT - влево \nRIGHT - вправо \nSPACE (Editor) - поставить/удалить " +
                                "препятствие\nESCAPE - открыть мини-меню в игре", "Help",
                        JOptionPane.PLAIN_MESSAGE);
            }
        });

        // кнопка Quit
        JButton quitButton = new JButton("Quit");
        quitButton.setBounds(20, 140, 100, 30);
        editorMenuPanel.add(quitButton);
        quitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setGameState(GameState.QUIT); // выход из игры
                editorMenu.dispose();
            }
        });

        editorMenu.add(editorMenuPanel); // добавить панель с кнопками в окно
        editorMenu.pack(); // укладка всего

        editorMenu.setVisible(true); // сделать видимым для пользователя

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
