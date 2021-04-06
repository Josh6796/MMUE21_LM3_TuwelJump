package com.example.mmue_lm3.gameobjects;

import android.graphics.Canvas;

public class ProfessorObject extends GameObject {

    private int health;
    private final int ects;

    public ProfessorObject(int health, int ects) {
        this.health = health;
        this.ects = ects;
    }

    @Override
    public void draw(Canvas canvas) {

    }

    public int getHealth() {
        return health;
    }

    public int getEcts() {
        return ects;
    }

    public void removeHealth(int health) {
        if (this.health - health >= 0) {
            this.health -= health;
        } else {
            this.health = 0;
        }
    }
}
