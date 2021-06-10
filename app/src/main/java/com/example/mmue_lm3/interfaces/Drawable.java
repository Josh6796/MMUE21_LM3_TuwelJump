package com.example.mmue_lm3.interfaces;

import android.graphics.Canvas;

import com.example.mmue_lm3.util.Camera;

/**
 * Interface for Drawable GameObjects which implements the draw Method for GameObjects and enables
 * us to draw on the Canvas.
 *
 * @author Joshua Oblong
 */
public interface Drawable {

    /**
     * Method for drawing the GameObjects on the Canvas
     *
     * @param camera for indicating the Players perspective, so we can move the background
     * @param canvas that wil be drawn onto
     */
    void draw(Camera camera, Canvas canvas);
}
