package com.example.mmue_lm3.gameobjects;

import com.example.mmue_lm3.interfaces.Collidable;

/**
 * Item GameObject
 *
 * @author Joshua Oblong
 */
public abstract class ItemObject extends GameObject implements Collidable {

    private static final int PRIORITY = 2;

    protected ItemObject(double x, double y, int width, int height) {
        super(x, y, width, height, PRIORITY);
    }

}
