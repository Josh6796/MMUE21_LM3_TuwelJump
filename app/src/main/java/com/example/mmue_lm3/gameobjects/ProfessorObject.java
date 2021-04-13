package com.example.mmue_lm3.gameobjects;

import android.graphics.Canvas;

import com.example.mmue_lm3.Camera;
import com.example.mmue_lm3.Scene;
import com.example.mmue_lm3.interfaces.Collidable;

/**
 * Professor GameObject
 *
 * @author Joshua Oblong
 */
public class ProfessorObject extends GameObject implements Collidable {

    private static final int PRIORITY = 2;

    private int health;
    private final int ects;

    public ProfessorObject(int health, int ects, double x, double y) {
        super(x, y, 10, 20, PRIORITY);
        this.health = health;
        this.ects = ects;
    }

    @Override
    public void draw(Camera camera, Canvas canvas) {

    }

    @Override
    public void update(double deltaTime) {

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

    @Override
    public boolean collide(Scene scene, CharacterObject character) {
        // TODO: do something...
        return false;
    }
}
