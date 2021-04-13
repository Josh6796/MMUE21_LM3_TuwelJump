/**
 * Booster Item GameObject
 *
 * @author Joshua Oblong
 */

package com.example.mmue_lm3.gameobjects;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

import com.example.mmue_lm3.Camera;
import com.example.mmue_lm3.enums.Booster;

public class BoosterItemObject extends ItemObject {

    private final Booster booster;

    public BoosterItemObject(Booster booster, double x, double y) {
        super(x, y, 50, 50);
        this.booster = booster;
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
        Paint paint = new Paint();
        paint.setColor(Color.rgb(102, 22, 153));
        paint.setStyle(Paint.Style.FILL);

        Rect rect = this.getRectangle();
        rect.offset(-camera.getX(), -camera.getY());
        canvas.drawRect(rect, paint);
    }

    private void drawInvisibility(Camera camera, Canvas canvas) {
        Paint paint = new Paint();
        paint.setColor(Color.rgb(170, 249, 249));
        paint.setStyle(Paint.Style.FILL);

        Rect rect = this.getRectangle();
        rect.offset(-camera.getX(), -camera.getY());
        canvas.drawRect(rect, paint);
    }

    private void drawSlowMotion(Camera camera, Canvas canvas) {
        Paint paint = new Paint();
        paint.setColor(Color.rgb(85, 188, 227));
        paint.setStyle(Paint.Style.FILL);

        Rect rect = this.getRectangle();
        rect.offset(-camera.getX(), -camera.getY());
        canvas.drawRect(rect, paint);
    }

    private void drawDamage(Camera camera, Canvas canvas) {
        Paint paint = new Paint();
        paint.setColor(Color.rgb(255, 123, 64));
        paint.setStyle(Paint.Style.FILL);

        Rect rect = this.getRectangle();
        rect.offset(-camera.getX(), -camera.getY());
        canvas.drawRect(rect, paint);
    }

    public Booster getBooster() {
        return booster;
    }

    @Override
    public void update(double deltaTime) {

    }

    @Override
    public void consumedBy(CharacterObject character) {
        character.consume(this);
    }
}
