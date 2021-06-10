package com.example.mmue_lm3.gameobjects;

import android.graphics.Canvas;

import com.example.mmue_lm3.util.Camera;
import com.example.mmue_lm3.Scene;
import com.example.mmue_lm3.sprites.DynamicBitmap;
import com.example.mmue_lm3.sprites.TimeAnimatedSprite;

/**
 * ECTS Item GameObject which is there for creating, drawing, updating an detecting collision.
 * It extends the ItemObject Class.
 *
 * @author Joshua Oblong
 */
public class EctsItemObject extends ItemObject {

    private final int ects;
    private final TimeAnimatedSprite timeAnimatedSprite;

    public EctsItemObject(DynamicBitmap bitmap, int ects, double x, double y) {
        super(x, y, 0, 0);
        this.timeAnimatedSprite = new TimeAnimatedSprite(bitmap, 6, 0.15);
        this.ects = ects;

        super.setWidth(this.timeAnimatedSprite.getWidth());
        super.setHeight(this.timeAnimatedSprite.getHeight());
    }

    @Override
    public void draw(Camera camera, Canvas canvas) {
        timeAnimatedSprite.draw(canvas, getScreenX(camera), getScreenY(camera), width, height);
    }

    @Override
    public void update(double deltaTime) {
        timeAnimatedSprite.update(deltaTime);
    }

    public int getEcts() {
        return ects;
    }

    @Override
    public boolean collide(Scene scene, CharacterObject character) {
        character.consume(this);
        scene.remove(this);
        return true;
    }
}
