package com.example.mmue_lm3.gameobjects;

/**
 * Item GameObject
 *
 * @author Joshua Oblong
 */
public abstract class ItemObject extends GameObject {

    private static final int PRIORITY = 1;

    protected ItemObject(double x, double y, int width, int height) {
        super(x, y, width, height, PRIORITY);
    }

    abstract public void consumedBy(CharacterObject character);
}
