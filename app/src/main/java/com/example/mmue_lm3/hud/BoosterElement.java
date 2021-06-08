package com.example.mmue_lm3.hud;

import android.graphics.Canvas;

import com.example.mmue_lm3.sprites.Sprite;

/**
 * Booster Hud Element which shows the active Booster Icons on the top of the screen.
 *
 * @author Mathias Schwengerer
 */
public class BoosterElement extends HudElement {

    public BoosterElement(Sprite sprite, int screenX, int screenY, int width, int height) {
        super(sprite, screenX, screenY, width, height);
    }

    public void draw(Canvas canvas, int xOffset) {
        sprite.draw(canvas, screenX + xOffset, screenY, width, height);
    }

    public int getWidth() {
        return width;
    }

}
