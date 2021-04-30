package com.example.mmue_lm3.sprites;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;

/**
 * Represents a time based animated sprite.
 *
 * @author Joshua Oblong
 */
public class TimeAnimatedSprite extends Sprite{
    private final double frameTime;
    private final boolean repeat;

    private final int startFrame;
    private final int endFrame;

    private double time;

    private boolean finished;

    public TimeAnimatedSprite(Resources res, int id, int frames, double frameTime, int startFrame, int endFrame, boolean repeat) {
        super(res, id, frames, startFrame);

        this.frameTime = frameTime;

        this.repeat = repeat;

        this.startFrame = startFrame;
        this.endFrame = endFrame;
        this.time = 0;

        this.finished = false;
    }

    public void update(double deltaTime) {
        if (finished) return;

        this.time += deltaTime;

        if (time >= frameTime) {
            time -= frameTime;

            frame++;
            frame %= frames;

            if (frame == endFrame && !repeat)
                finished = true;
        }
    }

}
