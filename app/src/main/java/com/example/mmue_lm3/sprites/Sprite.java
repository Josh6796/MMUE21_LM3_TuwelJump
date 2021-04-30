package com.example.mmue_lm3.sprites;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;

/**
 * Represents a basic sprite that can be rendered on a {@link Canvas}.
 */
public class Sprite {

    private final DynamicBitmap spriteSheet;

    protected final int frames;

    protected final int frameWidth;
    protected final int frameHeight;

    protected int frame;

    public Sprite(DynamicBitmap bitmap, int frames, int frame) {
        spriteSheet = bitmap;
        this.frames = frames;
        this.frame = frame;

        frameWidth = spriteSheet.getWidth() / frames;
        frameHeight = spriteSheet.getHeight();
    }

    public void draw(Canvas canvas, int canvasX, int canvasY, int width, int height) {
        if (canvas != null) {
            Rect sourceRect = sourceRect();
            Rect targetRect = targetRect(canvasX, canvasY, width, height);
            canvas.drawBitmap(bitmap(), sourceRect, targetRect, null);
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

    protected Bitmap bitmap() {
        return spriteSheet.get();
    }
}
