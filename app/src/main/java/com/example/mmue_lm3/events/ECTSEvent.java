package com.example.mmue_lm3.events;

import com.example.mmue_lm3.interfaces.Event;

/**
 Event for handling ECTS changes

 @author Mathias Schwengerer
 */
public class ECTSEvent implements Event {

    private final int ects;

    public ECTSEvent(int ects) {
        this.ects = ects;
    }

    public int getEcts() {
        return ects;
    }
}
