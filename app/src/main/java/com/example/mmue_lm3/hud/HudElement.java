package com.example.mmue_lm3.hud;

import android.graphics.Canvas;

import com.example.mmue_lm3.Camera;

public abstract class HudElement {

    protected double x;
    protected double y;

    public HudElement(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public abstract void draw(Canvas canvas);

}
