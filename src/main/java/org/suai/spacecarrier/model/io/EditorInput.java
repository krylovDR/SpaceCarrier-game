package org.suai.spacecarrier.model.io;

import org.suai.spacecarrier.model.editor.EditLevel;
import org.suai.spacecarrier.model.editor.User;
import org.suai.spacecarrier.model.level.Level;
import org.suai.spacecarrier.model.utils.ResourceLoader;
import org.suai.spacecarrier.view.panels.EditorMenu;
import org.suai.spacecarrier.view.panels.GameState;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class EditorInput implements KeyListener {

    private User user; // создание активной ячейки пользователя
    private EditLevel level; // создание уровня для редактирования
    private GameState gs = GameState.CONTINUE; // состояние редактора

    public EditorInput (String fileName) {
        level = new EditLevel(fileName);
        user = new User();
    }

    public void keyTyped (KeyEvent key) { }

    // обработка нажатия на клавишу
    public void keyPressed (KeyEvent key) {
        if (key.getKeyCode() == KeyEvent.VK_UP) {
            user.goUp();
        } else if (key.getKeyCode() == KeyEvent.VK_DOWN) {
            user.goDown();
        } else if (key.getKeyCode() == KeyEvent.VK_LEFT) {
            user.goLeft();
        } else if (key.getKeyCode() == KeyEvent.VK_RIGHT) {
            user.goRight();
        } else if (key.getKeyCode() == KeyEvent.VK_SPACE) {
            user.goSwap(level);
        } else if (key.getKeyCode() == KeyEvent.VK_ESCAPE) {

            EditorMenu.editorMenuLauncher(); // открыть мини-меню

            if (EditorMenu.getGameState() == GameState.CONTINUE) {

                EditorMenu.setCreated(false); // пометить окно мини-меню закрытым

            } else if (EditorMenu.getGameState() == GameState.SAVE) {

                EditorMenu.setCreated(false);
                ResourceLoader.exportLevel(level.getTileMap(), Level.LEVEL_NAME);

            } else if (EditorMenu.getGameState() == GameState.QUIT) {

                EditorMenu.setCreated(false);
                gs = GameState.QUIT;

            }
        }
    }

    public void keyReleased (KeyEvent key) { }

    // вывод на экран активной ячейки
    public void renderUser (Graphics2D g) {
        user.render(g);
    }

    // вывод на экран уровня
    public void renderLevel (Graphics2D g) {
        level.render(g);
    }

    // геттер состояния редактора
    public GameState getGS () {
        return gs;
    }

    // геттер для уровня
    public EditLevel getLevel () {
        return level;
    }
}
