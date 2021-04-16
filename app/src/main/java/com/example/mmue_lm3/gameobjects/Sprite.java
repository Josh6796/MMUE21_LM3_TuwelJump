package com.example.mmue_lm3.gameobjects;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

import com.example.mmue_lm3.Camera;

/**
 * Class for Sprite Creation
 *
 * @author Joshua Oblong (Demo as Template)
 */
public class Sprite {
    private final Bitmap sprite;
    private double x;
    private double y;
    private int frameWidth;
    private int frameHeight;
    private final int totalFrames;
    private int currentFrame;

    private long pastTime = 0;
    private final int frameTime;

    public Sprite(Bitmap sprite, double x, double y, int totalFrames, int frameTime) {
        this.sprite = sprite;
        this.totalFrames = totalFrames;
        this.currentFrame = 0;
        this.frameWidth = sprite.getWidth() / totalFrames;
        this.frameHeight = sprite.getHeight();
        this.x = x;
        this.y = y;
        this.frameTime = frameTime;

    }

    // Prüfen ob es Zeit für den nächsten Frame ist
    public void update(long currentTime) {
        if (currentTime > pastTime + frameTime) {
            pastTime = currentTime;
            currentFrame++;
            currentFrame %= totalFrames;
        }
    }

    // Zeichnen des aktuellen Frames auf das Canvas
    public void draw(Camera camera, Canvas canvas) {
        if (canvas != null) {
            Rect targetRect = new Rect((int)x, (int)y, (int)x + frameWidth, (int)y + frameHeight);
            Rect sourceRect = new Rect(currentFrame * frameWidth, 0, (currentFrame + 1) * frameWidth, frameHeight);

            targetRect.offset(-(int)camera.getX(), -(int)camera.getY());

            canvas.drawBitmap(sprite, sourceRect, targetRect, null);
        }
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public int getFrameWidth() {
        return frameWidth;
    }

    public void setFrameWidth(int frameWidth) {
        this.frameWidth = frameWidth;
    }

    public int getFrameHeight() {
        return frameHeight;
    }

    public void setFrameHeight(int frameHeight) {
        this.frameHeight = frameHeight;
    }
}
