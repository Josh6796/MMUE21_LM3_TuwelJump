package com.example.mmue_lm3.gameobjects;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import com.example.mmue_lm3.Camera;
import com.example.mmue_lm3.Scene;
import com.example.mmue_lm3.enums.Booster;

/**
 * Booster Item GameObject
 *
 * @author Joshua Oblong
 */
public class BoosterItemObject extends ItemObject {

    private final Booster booster;
    private final TimeAnimatedSprite timeAnimatedSprite;

    public BoosterItemObject(Bitmap bitmap, Booster booster, double x, double y) {
        super(x, y, 0, 0);
        this.booster = booster;

        timeAnimatedSprite = new TimeAnimatedSprite(bitmap, 4, 0.15);

        super.setHeight(timeAnimatedSprite.getHeight());
        super.setWidth(timeAnimatedSprite.getWidth());
    }

    @Override
    public void draw(Camera camera, Canvas canvas) {
        timeAnimatedSprite.draw(canvas, getScreenX(camera), getScreenY(camera), width, height);
    }

    public Booster getBooster() {
        return booster;
    }

    @Override
    public void update(double deltaTime) {
        timeAnimatedSprite.update(deltaTime);
    }

    @Override
    public boolean collide(Scene scene, CharacterObject character) {
        character.consume(this);
        scene.remove(this);
        return true;
    }
}
