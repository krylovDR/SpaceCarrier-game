package org.suai.spacecarrier.view.panels;

public enum GameState {

    CONTINUE(0),
    WAIT(1),
    RESTART(2),
    QUIT(3),
    LOSE(4),
    WIN(5),
    SAVE(6);

    private int n;

    GameState (int n) {
        this.n = n;
    }
}
