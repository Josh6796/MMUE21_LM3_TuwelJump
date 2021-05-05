package com.example.mmue_lm3.gameobjects;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import com.example.mmue_lm3.Camera;
import com.example.mmue_lm3.Scene;
import com.example.mmue_lm3.enums.Booster;
import com.example.mmue_lm3.interfaces.Collidable;
import com.example.mmue_lm3.sprites.DynamicBitmap;
import com.example.mmue_lm3.sprites.TimeAnimatedSprite;

/**
 * Professor GameObject
 *
 * @author Joshua Oblong
 */
public class ProfessorObject extends GameObject implements Collidable {

    private static final int PRIORITY = 2;

    private final TimeAnimatedSprite timeAnimatedSprite;

    private int health;
    private final int ects;

    public ProfessorObject(DynamicBitmap bitmap, int health, int ects, int x, int y) {
        super(x, y, 0, 0, PRIORITY);
        this.timeAnimatedSprite = new TimeAnimatedSprite(bitmap, 8, 0.15);
        this.health = health;
        this.ects = ects;

        super.setWidth(timeAnimatedSprite.getWidth());
        super.setHeight(timeAnimatedSprite.getHeight());
    }

    @Override
    public void draw(Camera camera, Canvas canvas) {
        timeAnimatedSprite.draw(canvas, getScreenX(camera), getScreenY(camera), width, height);
    }

    @Override
    public void update(double deltaTime) {
        timeAnimatedSprite.update(deltaTime);
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
            health -= character.isActive(Booster.Damage) ? 2 : 1;
            if (health <= 0) {
                character.addEcts(ects);
                scene.remove(this);
            }
            return true;
        }

        if (character.lastRight() - 1 <= this.left() && character.right() >= this.left()) {
            if(!character.isActive(Booster.Invincibility))
                character.addHealth(false);
            scene.moveCamera(-200, 0);
            return true;
        }

        if (character.lastLeft() + 1 >= this.right() && character.left() <= this.right()) {
            if(!character.isActive(Booster.Invincibility))
                character.addHealth(false);
            scene.moveCamera(200, 0);

            return true;
        }

        if (character.lastTop() + 1 >= this.bottom() && character.top() <= this.bottom()) {
            if(!character.isActive(Booster.Invincibility))
                character.addHealth(false);
            character.move(0, 1);
            character.setVerticalVelocity(-CharacterObject.MAX_VELOCITY);
            return true;
        }


        return false;
    }
}
