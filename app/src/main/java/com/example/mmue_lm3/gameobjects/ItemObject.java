package com.example.mmue_lm3.gameobjects;

public abstract class ItemObject extends GameObject {

    private static final int PRIORITY = 1;

    protected ItemObject(int x, int y, int width, int height) {
        super(x, y, width, height, PRIORITY);
    }
}
