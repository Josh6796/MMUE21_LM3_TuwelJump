package com.example.mmue_lm3.gameobjects;

import android.graphics.Rect;

import com.example.mmue_lm3.interfaces.Collidable;
import com.example.mmue_lm3.interfaces.Drawable;

public abstract class GameObject implements Drawable, Collidable {
    int x;
    int y;
    int width;
    int height;

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    // Returns a rectangle matching the size of the object for collision detection.
    public Rect getRectangle() {
        return new Rect(x, y, x + width, y + height);
    }
}
