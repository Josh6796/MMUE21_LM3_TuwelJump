package com.example.mmue_lm3.interfaces;

/**
 * Interface for Updatable GameObjects
 *
 * @author Joshua Oblong
 */
public interface Updatable {

    /**
     * Method that handles what the GameObjects will do
     *
     * @param deltaTime Time between last update and new update
     */
    void update(double deltaTime);

}
