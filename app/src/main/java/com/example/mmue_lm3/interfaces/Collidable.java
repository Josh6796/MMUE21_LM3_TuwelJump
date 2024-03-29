

package com.example.mmue_lm3.interfaces;

import com.example.mmue_lm3.Scene;
import com.example.mmue_lm3.gameobjects.CharacterObject;

/**
 * Interface for Collidable GameObjects which implements the collide Method for GameObjects,
 * so we can detect the collision for each Object.
 *
 * @author Joshua Oblong
 */
public interface Collidable {

    /**
     * Method for collision detection with characters
     *
     * @param scene in which the collision happened
     * @param character of the collision
     * @return true if the object collided with the character
     */
    boolean collide(Scene scene, CharacterObject character);
}
