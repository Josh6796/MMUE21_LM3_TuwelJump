package com.example.mmue_lm3.gameobjects;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import com.example.mmue_lm3.Camera;
import com.example.mmue_lm3.Scene;

/**
 * ECTS Item GameObject
 *
 * @author Joshua Oblong
 */
public class EctsItemObject extends ItemObject {

    private final int ects;
    private final Sprite sprite;

    public EctsItemObject(Bitmap bitmap, int ects, double x, double y) {
        super(x, y, 0, 0);

        this.ects = ects;

        this.sprite = new Sprite(bitmap, x, y, 6, 50);

        super.setWidth(this.sprite.getFrameWidth());
        super.setHeight(this.sprite.getFrameHeight());
    }

    @Override
    public void draw(Camera camera, Canvas canvas) {
        sprite.draw(camera, canvas);
    }

    @Override
    public void update(double deltaTime) {
        sprite.update(System.currentTimeMillis());
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
