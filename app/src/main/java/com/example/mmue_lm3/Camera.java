/**
 * Class for the Camera
 *
 * @author Mathias Schwengerer
 */

package com.example.mmue_lm3;

public class Camera {

    private int x, y;
    private int width, height;

    public Camera(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public void move(int x, int y) {
        this.x += x;
        this.y += y;
    }

    public void moveVertical(int y) {
        this.y = y;
    }

    public void moveHorizontal(int x) {
        this.x += x;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getCenterX() {
        return x + width / 2;
    }
}
