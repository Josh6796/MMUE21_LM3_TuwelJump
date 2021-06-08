package com.example.mmue_lm3.events;

import com.example.mmue_lm3.interfaces.Event;

/**
 * Basic event for handling health updates so we know when the character has lost a life.
 *
 * @author Mathias Schwengerer
 */
public class HealthEvent implements Event {

    private final boolean add;

    public HealthEvent(boolean add) {
        this.add = add;
    }

    public boolean add() {
        return  add;
    }
}
