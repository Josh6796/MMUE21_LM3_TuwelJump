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

    private final TimeAnimatedSprite timeAnimatedSpriteForward;
    private final TimeAnimatedSprite timeAnimatedSpriteBackward;

    private final int startX;
    private final int startY;
    private final int endX;
    private final int endY;
    private final float speed;
    private boolean forward;

    private int health;
    private final int ects;

    public ProfessorObject(DynamicBitmap bitmapForward, DynamicBitmap bitmapBackward, int health, int ects, int startX, int startY, int endX, int endY, float speed) {
        super(startX, startY, 0, 0, PRIORITY);

        this.timeAnimatedSpriteForward = new TimeAnimatedSprite(bitmapForward, 8, 0.15);
        this.timeAnimatedSpriteBackward = new TimeAnimatedSprite(bitmapBackward, 8, 0.15);

        this.startX = startX;
        this.startY = startY;
        this.endX = endX;
        this.endY = endY;
        this.speed = speed;

        this.health = health;
        this.ects = ects;

        super.setWidth(timeAnimatedSpriteForward.getWidth());
        super.setHeight(timeAnimatedSpriteForward.getHeight());
    }

    @Override
    public void draw(Camera camera, Canvas canvas) {
        if (forward)
            timeAnimatedSpriteForward.draw(canvas, getScreenX(camera), getScreenY(camera), width, height);
        else
            timeAnimatedSpriteBackward.draw(canvas, getScreenX(camera), getScreenY(camera), width, height);
    }

    @Override
    public void update(double deltaTime) {
        move(deltaTime);
        timeAnimatedSpriteForward.update(deltaTime);
        timeAnimatedSpriteBackward.update(deltaTime);
    }

    private void move(double deltaTime) {
        double deltaX = endX - startX;
        double deltaY = endY - startY;

        if (deltaX == 0 && deltaY == 0)
            return;

        if (!forward) {
            deltaX *= -1;
            deltaY *= -1;
        }

        double magnitude = Math.sqrt(deltaX * deltaX + deltaY * deltaY);
        double movementX = deltaX / magnitude * speed * deltaTime;
        double movementY = deltaY / magnitude * speed * deltaTime;

        this.worldX += movementX;
        this.worldY += movementY;

        if (deltaX > 0 && worldX > (forward ? endX : startX))
            forward = !forward;
        else if (deltaX < 0 && worldX < (forward ? endX : startX))
            forward = !forward;
        else if (deltaY > 0 && worldY > (forward ? endY : startY))
            forward = !forward;
        else if (deltaY < 0 && worldY < (forward ? endY : startY))
            forward = !forward;
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
        if (character.lastBottom() - 3 <= this.top() && character.bottom() >= this.top()) {
            character.jump(scene);
            health -= character.isActive(Booster.Damage) ? 2 : 1;
            if (health <= 0) {
                character.addEcts(ects);
                scene.remove(this);
            }
            return true;
        }

        if (character.lastRight() - 3 <= this.left() && character.right() >= this.left()) {
            if (!character.isActive(Booster.Invincibility))
                character.addHealth(false);
            scene.moveCamera(-200, 0);
            return true;
        }

        if (character.lastLeft() + 3 >= this.right() && character.left() <= this.right()) {
            if (!character.isActive(Booster.Invincibility))
                character.addHealth(false);
            scene.moveCamera(200, 0);

            return true;
        }

        if (character.lastTop() + 3 >= this.bottom() && character.top() <= this.bottom()) {
            if (!character.isActive(Booster.Invincibility))
                character.addHealth(false);
            character.move(0, 1);
            character.setVerticalVelocity(-CharacterObject.MAX_VELOCITY);
            return true;
        }


        return false;
    }
}
