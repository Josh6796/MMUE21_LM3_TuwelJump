/**
 * Class for the Camera
 *
 * @author Mathias Schwengerer
 */

package com.example.mmue_lm3;

public class Camera {

    private int x, y;

    public Camera(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void move(int x, int y) {
        this.x += x;
        this.y += y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
