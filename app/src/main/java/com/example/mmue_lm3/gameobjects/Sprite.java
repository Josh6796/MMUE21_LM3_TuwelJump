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
    private final Bitmap spriteSheet;
    private final int frames;
    private final double frameTime;

    private final boolean repeat;

    private final int frameWidth;
    private final int frameHeight;

    private double worldX;
    private double worldY;

    private final int startFrame;
    private final int endFrame;
    private int currentFrame;
    private double time;

    private boolean finished;

    public Sprite(Bitmap spriteSheet, int frames, double frameTime, int startFrame, int endFrame, boolean repeat, double worldX, double worldY) {
        this.spriteSheet = spriteSheet;
        this.frames = frames;
        this.frameTime = frameTime;

        this.worldX = worldX;
        this.worldY = worldY;

        this.repeat = repeat;

        this.frameWidth = spriteSheet.getWidth() / frames;
        this.frameHeight = spriteSheet.getHeight();

        this.startFrame = startFrame;
        this.endFrame = endFrame;
        this.currentFrame = startFrame;
        this.time = 0;

        this.finished = false;
    }

    public Sprite(Bitmap spriteSheet, int totalFrames, double frameTime, double x, double y) {
        this(spriteSheet, totalFrames, frameTime, 0, totalFrames - 1, true, x, y);
    }

    public void update(double deltaTime) {
        if (finished) return;

        this.time += deltaTime;

        if (time >= frameTime) {
            time -= frameTime;

            currentFrame++;
            currentFrame %= frames;

            if (currentFrame == endFrame && !repeat)
                finished = true;
        }
    }

    public void draw(Camera camera, Canvas canvas) {
        if (canvas != null) {
            Rect sourceRect = new Rect(currentFrame * frameWidth, 0, (currentFrame + 1) * frameWidth, frameHeight);
            Rect targetRect = new Rect((int) worldX, (int) worldY, (int) worldX + frameWidth, (int) worldY + frameHeight);

            targetRect.offset(-(int) camera.getX(), -(int) camera.getY());

            canvas.drawBitmap(spriteSheet, sourceRect, targetRect, null);
        }
    }

    public int getFrameWidth() {
        return frameWidth;
    }

    public int getFrameHeight() {
        return frameHeight;
    }

    public void setWorldPos(double x, double y) {
        this.worldX = x;
        this.worldY = y;
    }

    public void setWorldX(double x) {
        this.worldX = x;
    }

    public void setWorldY(double y) {
        this.worldY = y;
    }
}
