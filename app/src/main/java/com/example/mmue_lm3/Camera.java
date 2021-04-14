package com.example.mmue_lm3;

import com.example.mmue_lm3.interfaces.Updatable;

/**
 * Class for the Camera
 *
 * @author Mathias Schwengerer
 */
public class Camera implements Updatable {

    private double x, y;
    private int width, height;
    private double movementX;
    private double movementY;

    public Camera(double x, double y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public void move(double x, double y) {
        this.movementY += y;
        this.movementX += x;
    }

    public void moveVertical(int y) {
        this.y = y;
    }

    public void moveHorizontal(int x) {
        this.x += x;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getCenterX() {
        return x + width / 2.0;
    }

    @Override
    public void update(double deltaTime) {
        double moveY = this.movementY * 5 * deltaTime;
        movementY -= moveY;
        this.y += moveY;

        double moveX = this.movementX * 5 * deltaTime;
        movementX -= moveX;
        this.x += moveX;
    }
}
