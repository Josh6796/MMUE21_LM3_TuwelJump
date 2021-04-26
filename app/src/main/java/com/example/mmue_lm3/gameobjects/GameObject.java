package com.example.mmue_lm3.gameobjects;

import android.graphics.Rect;

import com.example.mmue_lm3.Camera;
import com.example.mmue_lm3.interfaces.Drawable;
import com.example.mmue_lm3.interfaces.Updatable;

/**
 * Main GameObject
 *
 * @author Joshua Oblong (Demo as Template)
 */
public abstract class GameObject implements Drawable, Updatable, Comparable<GameObject> {
    double worldX;
    double worldY;
    int width;
    int height;
    int priority;

    protected GameObject(double worldX, double worldY, int width, int height, int priority) {
        this.worldX = worldX;
        this.worldY = worldY;
        this.width = width;
        this.height = height;
        this.priority = priority;
    }

    public void setWorldX(double worldX) {
        this.worldX = worldX;
    }

    public void setWorldY(double worldY) {
        this.worldY = worldY;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public double getWorldX() {
        return worldX;
    }

    public double getWorldY() {
        return worldY;
    }

    public int getScreenX(Camera camera) {
        return (int) (worldX - camera.getX());
    }

    public int getScreenY(Camera camera) {
        return (int) (worldY - camera.getY());
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public double bottom() {
        return worldY + height;
    }

    public double top() {
        return worldY;
    }

    public double left() {
        return worldX;
    }

    public double right() {
        return worldX + width;
    }

    // Returns a rectangle matching the size of the object for collision detection.
    public Rect getRectangle() {
        int x = (int) this.worldX;
        int y = (int) this.worldY;
        return new Rect(x, y, x + width, y + height);
    }

    //  public Ract getRectangle(float)

    @Override
    public int compareTo(GameObject o) {
        if (o.priority < this.priority)
            return 1;
        else if (o.priority > this.priority)
            return -1;
        else if (o.hashCode() < this.hashCode())
            return 1;
        else if (o.hashCode() > this.hashCode())
            return -1;
        else
            return 0;
    }
}