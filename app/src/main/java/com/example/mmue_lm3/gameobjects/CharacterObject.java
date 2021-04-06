package com.example.mmue_lm3.gameobjects;

import android.graphics.Canvas;

public class CharacterObject extends GameObject {

    private static final int MAX_HEALTH = 3;

    private int health;
    private int ects;

    public CharacterObject(int health, int ects, int x, int y) {
        super(x, y, 10, 20);
        this.health = health;
        this.ects = ects;
    }

    @Override
    public void draw(Canvas canvas) {

    }

    @Override
    public void update(float deltaTime) {

    }

    public int getHealth() {
        return health;
    }

    public int getEcts() {
        return ects;
    }

    public void addEcts(int ects) {
        this.ects += ects;
    }

    public void addHealth(int health) {
        if (this.health + health <= MAX_HEALTH) {
            if (this.health + health >= 0) {
                this.health += health;
            } else {
                this.health = 0;
            }
        } else {
            this.health = MAX_HEALTH;
        }
    }
}
