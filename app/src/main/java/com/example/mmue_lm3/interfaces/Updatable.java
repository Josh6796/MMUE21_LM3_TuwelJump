/**
 * Interface for Updatable GameObjects
 *
 * @author Joshua Oblong
 */

package com.example.mmue_lm3.interfaces;

public interface Updatable {

    /**
     * Method that handles what the GameObjects will do
     *
     * @param deltaTime Time between last update and new update
     */
    void update(double deltaTime);

}
