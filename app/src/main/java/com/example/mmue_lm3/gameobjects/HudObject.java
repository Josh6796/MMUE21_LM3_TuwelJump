package com.example.mmue_lm3.gameobjects;

/**
 * Item GameObject
 *
 * @author Joshua Oblong
 */
public abstract class HudObject extends GameObject {

    private static final int PRIORITY = 4;

    protected HudObject(double x, double y, int width, int height) {
        super(x, y, width, height, PRIORITY);
    }

}
