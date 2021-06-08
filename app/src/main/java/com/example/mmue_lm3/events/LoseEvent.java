
package com.example.mmue_lm3.events;

import com.example.mmue_lm3.interfaces.Event;

/**
 * Event for Handling Losing which gives us the insight from outside the game
 * if we can make an intent to the LoseActivity.
 *
 * @author Joshua Oblong
 */
public class LoseEvent implements Event {
    private final int score;

    public LoseEvent(int score) {
        this.score = score;
    }

    public int getScore() {
        return score;
    }
}
