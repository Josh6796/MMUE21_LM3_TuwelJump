package com.example.mmue_lm3.events;

import com.example.mmue_lm3.interfaces.Event;

/**
 * Event for handling Touchscreen Usage which enables us to know wherever when the screen is
 * touched and in what direction
 *
 * @author Mathias Schwengerer
 */
public class TouchEvent implements Event {

    private final int x;
    private final int y;

    public TouchEvent(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    @Override
    public String toString() {
        return "TouchEvent{x:" + x + ", y:" + y + "}";
    }
}
