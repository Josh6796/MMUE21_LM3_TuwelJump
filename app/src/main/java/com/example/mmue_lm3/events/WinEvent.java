
package com.example.mmue_lm3.events;

import com.example.mmue_lm3.interfaces.Event;

/**
 * Event for Handling Winning
 *
 * @author Joshua Oblong
 */
public class WinEvent implements Event {
    private final int score;

    public WinEvent(int score) {
        this.score = score;
    }

    public int getScore() {
        return score;
    }
}
