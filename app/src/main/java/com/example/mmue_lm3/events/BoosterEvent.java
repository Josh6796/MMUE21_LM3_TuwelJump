package com.example.mmue_lm3.events;

import com.example.mmue_lm3.enums.Booster;
import com.example.mmue_lm3.interfaces.Event;

/**
 Event for handling Booster

 @author Mathias Schwengerer
 */
public class BoosterEvent implements Event {

    private final Booster type;

    public BoosterEvent(Booster booster) {
        this.type = booster;
    }

    public Booster getType() {
        return type;
    }
}
