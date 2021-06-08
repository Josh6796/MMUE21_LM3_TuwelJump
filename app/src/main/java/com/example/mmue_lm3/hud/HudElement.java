package com.example.mmue_lm3.hud;

import android.graphics.Canvas;

import com.example.mmue_lm3.sprites.Sprite;

/**
 * Base {@code class} for HUD elements.
 *
 * @author Mathias Schwengerer
 */
public class HudElement {

    protected Sprite sprite;
    protected int screenX;
    protected int screenY;
    protected int width;
    protected int height;

    public HudElement(Sprite sprite, int screenX, int screenY, int width, int height) {
        this.sprite = sprite;
        this.screenX = screenX;
        this.screenY = screenY;
        this.width = width;
        this.height = height;
    }

    public void draw(Canvas canvas) {
        sprite.draw(canvas, screenX, screenY, width, height);
    }

}
