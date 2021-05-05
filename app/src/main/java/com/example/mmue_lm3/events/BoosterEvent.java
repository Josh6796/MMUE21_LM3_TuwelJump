package com.example.mmue_lm3.events;

import com.example.mmue_lm3.enums.Booster;
import com.example.mmue_lm3.interfaces.Event;

/**
 * Event for handling Booster
 *
 * @author Mathias Schwengerer
 */
public class BoosterEvent implements Event {

    private final Booster type;
    private final boolean active;

    public BoosterEvent(Booster booster, boolean active) {
        this.type = booster;
        this.active = active;
    }

    public Booster getType() {
        return type;
    }

    public boolean isActive() {
        return active;
    }
}
