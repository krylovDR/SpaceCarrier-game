package org.suai.spacecarrier.model.game;

import org.suai.spacecarrier.model.io.Input;

import java.awt.*;

public abstract class Entity {

    public final EntityType type; // тип Entity

    // местонахождение
    protected float x; // координата x
    protected float y; // координата y

    // конструктор
    protected Entity (EntityType type, float x, float y) {
        this.type = type;
        this.x = x;
        this.y = y;
    }

    public abstract void update (Input input);

    public abstract void render (Graphics2D g);

}
