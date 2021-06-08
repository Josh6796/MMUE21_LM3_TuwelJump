package com.example.mmue_lm3.sprites;

/**
 * Represents an event based animated sprite. Depending on the Event we can change the look of the
 * Sprite (e.g. for Jumping)
 *
 * @author Mathias Schwengerer
 */
public class EventAnimatedSprite extends Sprite {

    private final boolean repeat;

    private final int startFrame;
    private final int endFrame;

    private boolean finished;

    public EventAnimatedSprite(DynamicBitmap bitmap, int frames, int startFrame, int endFrame, boolean repeat) {
        super(bitmap, frames, startFrame);

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
