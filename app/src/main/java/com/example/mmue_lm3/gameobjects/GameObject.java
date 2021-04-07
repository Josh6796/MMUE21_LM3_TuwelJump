package com.example.mmue_lm3.gameobjects;

import android.graphics.Rect;

import com.example.mmue_lm3.interfaces.Collidable;
import com.example.mmue_lm3.interfaces.Drawable;
import com.example.mmue_lm3.interfaces.Updatable;

public abstract class GameObject implements Drawable, Collidable, Updatable, Comparable<GameObject> {
    int x;
    int y;
    int width;
    int height;
    int priority;

    protected GameObject(int x, int y, int width, int height, int priority) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.priority = priority;
    }

    @Override
    public int compareTo(GameObject o) {
        return o.priority < this.priority ? 1 : -1;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
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