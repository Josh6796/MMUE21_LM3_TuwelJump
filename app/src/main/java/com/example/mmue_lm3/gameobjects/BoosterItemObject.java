package com.example.mmue_lm3.gameobjects;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

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
    private Sprite sprite;

    public BoosterItemObject(Bitmap bitmap, Booster booster, double x, double y) {
        super(x, y, 0, 0);
        this.booster = booster;

        sprite = new Sprite(bitmap, x, y, 4, 150);

        super.setHeight(sprite.getFrameHeight());
        super.setWidth(sprite.getFrameWidth());
    }

    @Override
    public void draw(Camera camera, Canvas canvas) {
        switch (booster) {
            case Speed:
                drawSpeed(camera, canvas);
                break;
            case Invisibility:
                drawInvisibility(camera, canvas);
                break;
            case SlowMotion:
                drawSlowMotion(camera, canvas);
                break;
            case Damage:
                drawDamage(camera, canvas);
                break;
        }
    }

    private void drawSpeed(Camera camera, Canvas canvas) {
        sprite.draw(camera, canvas);
    }

    private void drawInvisibility(Camera camera, Canvas canvas) {
        Paint paint = new Paint();
        paint.setColor(Color.rgb(170, 249, 249));
        paint.setStyle(Paint.Style.FILL);

        Rect rect = this.getRectangle();
        rect.offset(-(int)camera.getX(), -(int)camera.getY());
        canvas.drawRect(rect, paint);
    }

    private void drawSlowMotion(Camera camera, Canvas canvas) {
        Paint paint = new Paint();
        paint.setColor(Color.rgb(85, 188, 227));
        paint.setStyle(Paint.Style.FILL);

        Rect rect = this.getRectangle();
        rect.offset(-(int)camera.getX(), -(int)camera.getY());
        canvas.drawRect(rect, paint);
    }

    private void drawDamage(Camera camera, Canvas canvas) {
        sprite.draw(camera, canvas);
    }

    public Booster getBooster() {
        return booster;
    }

    @Override
    public void update(double deltaTime) {
        sprite.update(System.currentTimeMillis());
    }

    @Override
    public boolean collide(Scene scene, CharacterObject character) {
        character.consume(this);
        scene.remove(this);
        return true;
    }
}
