package com.example.mmue_lm3;

import com.example.mmue_lm3.gameobjects.GameObject;

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
