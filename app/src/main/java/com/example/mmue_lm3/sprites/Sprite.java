package com.example.mmue_lm3.sprites;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

/**
 * Represents a basic sprite that can be rendered on a {@link Canvas}.
 */
public class Sprite {

    protected final Bitmap spriteSheet;
    protected final int frames;

    protected final int frameWidth;
    protected final int frameHeight;

    protected int frame;

    public Sprite(Bitmap spriteSheet, int frames, int frameWidth, int frameHeight, int frame) {
        this.spriteSheet = spriteSheet;
        this.frames = frames;

        this.frameWidth = frameWidth;
        this.frameHeight = frameHeight;

        this.frame = frame;
    }

    public void draw(Canvas canvas, int canvasX, int canvasY, int width, int height) {
        if (canvas != null) {
            Rect sourceRect = sourceRect();
            Rect targetRect = targetRect(canvasX, canvasY, width, height);
            canvas.drawBitmap(spriteSheet, sourceRect, targetRect, null);
        }
    }

    private Rect sourceRect() {
        return new Rect(frame * frameWidth, 0, (frame + 1) * frameWidth, frameHeight);
    }

    private Rect targetRect(int x, int y, int width, int height) {
        return new Rect(x, y, x + width, y + height);
    }

    public int getWidth() {
        return frameWidth;
    }

    public int getHeight() {
        return frameHeight;
    }
}
