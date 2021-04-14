package com.example.mmue_lm3.gameobjects;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

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
        super(x, y, 80, 100, PRIORITY);
        this.health = health;
        this.ects = ects;
    }

    @Override
    public void draw(Camera camera, Canvas canvas) {
        Paint paint = new Paint();
        paint.setColor(Color.rgb(0, 168, 107));
        paint.setStyle(Paint.Style.FILL);

        Rect rect = this.getRectangle();
        rect.offset(-(int) camera.getX(), -(int) camera.getY());
        canvas.drawRect(rect, paint);
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
        if (character.lastBottom() - 1 <= this.top() && character.bottom() >= this.top()) {
            character.jump(scene);
            health--;
            if (health <= 0) {
                character.addEcts(ects);
                scene.remove(this);
            }
            return true;
        }

        if (character.lastRight() - 1 <= this.left() && character.right() >= this.left()) {
            character.addHealth(-1);
            scene.moveCamera(-200, 0);
            return true;
        }

        if (character.lastLeft() + 1 >= this.right() && character.left() <= this.right()) {
            character.addHealth(-1);
            scene.moveCamera(200, 0);

            return true;
        }

        if (character.lastTop() + 1 >= this.bottom() && character.top() <= this.bottom()) {
            character.addHealth(-1);
            character.move(0, 1);
            character.setVerticalVelocity(-CharacterObject.MAX_VELOCITY);
            return true;
        }


        return false;
    }
}
