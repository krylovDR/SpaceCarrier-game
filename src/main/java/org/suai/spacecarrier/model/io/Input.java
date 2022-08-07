package org.suai.spacecarrier.model.io;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

public class Input extends JComponent {

    private boolean[] map; // массив кнопок (индекс - ASCII, значение - нажатие)

    // конструктор
    public Input() {
        map = new boolean[256]; // создание массива

        for (int i = 0; i < map.length; i++) {
            final int KEY_CODE = i; // ASCII код для анонимного вложенного класса

            // обработка нажатия кнопки, когда фокус на окне
            getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
                    KeyStroke.getKeyStroke(i, 0, false),
                    i * 2);
            // i * 2 - ключ кнопки
            // что делать при нажатии кнопки (анонимный вложенный класс)
            getActionMap().put(i * 2, new AbstractAction() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    map[KEY_CODE] = true;
                }
            });

            // обработка отпускания кнопки, когда фокус на окне
            getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
                    KeyStroke.getKeyStroke(i, 0, true),
                    i * 2 + 1);
            // i * 2 + 1 - ключ кнопки
            // что делать при отпускании кнопки (анонимный вложенный класс)
            getActionMap().put(i * 2 + 1, new AbstractAction() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    map[KEY_CODE] = false;
                }
            });
        }
    }

    // проверка, нажата ли клавиша
    public boolean getKey(int keyCode) {
        return map[keyCode]; // возвращаем нажата ли эта кнопка
    }

    // отжать все клавиши
    public void releaseKeys () {
        map[KeyEvent.VK_ESCAPE] = false;
        map[KeyEvent.VK_UP] = false;
        map[KeyEvent.VK_DOWN] = false;
        map[KeyEvent.VK_LEFT] = false;
        map[KeyEvent.VK_RIGHT] = false;
    }
}
