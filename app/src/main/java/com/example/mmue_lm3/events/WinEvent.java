
package com.example.mmue_lm3.events;

import com.example.mmue_lm3.interfaces.Event;

/**
 * Event for Handling Winning which gives us the insight from outside the game
 * if we can make an intent to the WinActivity.
 *
 * @author Joshua Oblong
 */
public class WinEvent implements Event {

    private final int level;
    private final int health;
    private final int ects;

    public WinEvent(int level, int health, int ects) {
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
