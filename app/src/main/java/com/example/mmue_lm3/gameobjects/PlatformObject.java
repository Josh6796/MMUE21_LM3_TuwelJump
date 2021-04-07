package com.example.mmue_lm3.gameobjects;

import android.graphics.Canvas;

public class PlatformObject extends GameObject {

    private static final int PRIORITY = 1;

    protected PlatformObject(int x, int y, int width, int height) {
        super(x, y, width, height, PRIORITY);
    }

    @Override
    public void draw(Canvas canvas) {

    }

    @Override
    public void update(float deltaTime) {

    }
}
