/**
 * Event for making Objects move
 *
 * @author Mathias Schwengerer
 */

package com.example.mmue_lm3.events;

import com.example.mmue_lm3.interfaces.Event;

public class VelocityEvent implements Event {

    private final float x;
    private final float y;

    public VelocityEvent(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }
}
