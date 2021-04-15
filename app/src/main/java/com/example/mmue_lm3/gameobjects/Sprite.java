package com.example.mmue_lm3.gameobjects;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

/**
 * Class for Sprite Creation
 *
 * @author Joshua Oblong (Demo as Template)
 */
public class Sprite {
    private final Bitmap sprite;
    private double x;
    private double y;
    private final int frameWidth;
    private final int frameHeight;
    private int totalFrames;
    private int currentFrame;

    long pastTime = 0;
    int frameTime;

    public Sprite(Bitmap sprite, int x, int y) {
        this.sprite = sprite;
        this.totalFrames = 6;
        this.currentFrame = 0;
        this.frameWidth = sprite.getWidth() / totalFrames;
        this.frameHeight = sprite.getHeight();
        this.x = x;
        this.y = y;
        frameTime = 150;

    }

    // Prüfen ob es Zeit für den nächsten Frame ist
    public void update(long currentTime) {
        if (currentTime > pastTime + frameTime) {
            pastTime = currentTime;
            currentFrame++;
            currentFrame %= 6;
        }
    }

    // Zeichnen des aktuellen Frames auf das Canvas
    public void draw(Canvas canvas) {
        if (canvas != null) {
            Rect targetRect = new Rect((int)x, (int)y, (int)x + frameWidth, (int)y + frameHeight);
            Rect sourceRect = new Rect(currentFrame * frameWidth + 2, 0, (currentFrame + 1) * frameWidth, frameHeight);
            canvas.drawBitmap(sprite, sourceRect, targetRect, null);
        }
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }
}
