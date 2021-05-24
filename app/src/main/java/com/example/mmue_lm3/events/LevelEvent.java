package com.example.mmue_lm3.events;

import com.example.mmue_lm3.interfaces.Event;

/**
 * Event for handling level initialization. Contains current game state.
 *
 * @author Mathias Schwengerer
 */
public class LevelEvent implements Event {

    private final int level;
    private final int health;
    private final int ects;

    public LevelEvent(int level, int health, int ects) {
        this.level = level;
        this.health = health;
        this.ects = ects;
    }

    public int getLevel() {
        return level;
    }

    public int getHealth() {
        return health;
    }

    public int getEcts() {
        return ects;
    }
}
