package com.example.mmue_lm3.sprites;

/**
 * Represents a time based animated sprite. Depending on the time we can change the
 * look of the Sprite.
 *
 * @author Joshua Oblong
 */
public class TimeAnimatedSprite extends Sprite {
    private final double frameTime;
    private final boolean repeat;

    private final int startFrame;
    private final int endFrame;

    private double time;

    private boolean finished;

    public TimeAnimatedSprite(DynamicBitmap bitmap, int frames, double frameTime, int startFrame, int endFrame, boolean repeat) {
        super(bitmap, frames, startFrame);

        this.frameTime = frameTime;

        this.repeat = repeat;

        this.startFrame = startFrame;
        this.endFrame = endFrame;
        this.time = 0;

        this.finished = false;
    }

    public TimeAnimatedSprite(DynamicBitmap bitmap, int frames, double frameTime) {
        this(bitmap, frames, frameTime, 0, frames - 1, true);
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
