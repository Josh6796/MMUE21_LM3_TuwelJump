package com.example.mmue_lm3.events;

import com.example.mmue_lm3.interfaces.Collidable;
import com.example.mmue_lm3.interfaces.Event;

/**
 * Event for handling Collision
 *
 * @author Joshua Oblong
 */
public class CollisionEvent implements Event {

    private final Collidable character;
    private final Collidable other;

    public CollisionEvent(Collidable character, Collidable other) {
        this.character = character;
        this.other = other;
    }

    public Collidable getCharacter() {
        return character;
    }

    public Collidable getOther() {
        return other;
    }
}
