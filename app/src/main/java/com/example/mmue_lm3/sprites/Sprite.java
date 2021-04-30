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

    private Bitmap spriteSheet;
    private final Resources resources;
    private final int resourceID;

    protected final int frames;

    protected int frameWidth;
    protected int frameHeight;

    protected int frame;

    public Sprite(Resources res, int id, int frames, int frame) {
        resources = res;
        resourceID = id;
        this.frames = frames;
        this.frame = frame;

        load();
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

    public void load() {
        if (spriteSheet != null)
            return;

        spriteSheet = BitmapFactory.decodeResource(resources, resourceID);
        frameWidth = spriteSheet.getWidth() / frames;
        frameHeight = spriteSheet.getHeight();
    }

    public void recycle() {
        if (spriteSheet == null)
            return;

        spriteSheet.recycle();
        spriteSheet = null;
        frameWidth = 0;
        frameHeight = 0;
    }

    protected Bitmap bitmap() {
        if (spriteSheet == null)
            load();
        return spriteSheet;
    }
}
