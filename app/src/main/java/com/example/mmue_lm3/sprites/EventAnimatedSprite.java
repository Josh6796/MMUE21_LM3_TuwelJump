package com.example.mmue_lm3.sprites;

import android.graphics.Bitmap;

/**
 * Represents an event based animated sprite.
 */
public class EventAnimatedSprite extends Sprite {

    private final boolean repeat;

    private final int startFrame;
    private final int endFrame;

    private boolean finished;

    public EventAnimatedSprite(Bitmap spriteSheet, int frames, int startFrame, int endFrame, boolean repeat) {
        super(spriteSheet, frames, spriteSheet.getWidth() / frames, spriteSheet.getHeight(), startFrame);

        this.repeat = repeat;

        this.startFrame = startFrame;
        this.endFrame = endFrame;
    }

    public void reset() {
        frame = startFrame;
        finished = false;
    }

    public void setFrame(int frame) {
        this.frame = frame;
    }

    public void update() {
        if (finished) return;

        frame++;
        frame %= frames;

        if (frame == endFrame && !repeat)
            finished = true;
    }

}
