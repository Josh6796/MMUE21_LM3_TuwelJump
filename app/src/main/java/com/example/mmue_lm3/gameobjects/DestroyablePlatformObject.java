package com.example.mmue_lm3.gameobjects;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

import com.example.mmue_lm3.util.Camera;
import com.example.mmue_lm3.Scene;

/**
 * Destroyable Platform GameObject which is there for creating, drawing, updating an detecting collision.
 * It extends the PlatformObject Class.
 *
 * @author Joshua Oblong
 */
public class DestroyablePlatformObject extends PlatformObject {

    private int health;

    public DestroyablePlatformObject(double x, double y, int width, int health) {
        super(x, y, width);
        this.health = health;
    }

    @Override
    public void draw(Camera camera, Canvas canvas) {
        Paint paint = new Paint();
        paint.setColor(Color.rgb(20 + 30 * health, 10, 5 + 30 * health));
        paint.setStyle(Paint.Style.FILL);

        Rect rect = this.getRectangle();
        rect.offset(-(int)camera.getX(), -(int)camera.getY());
        canvas.drawRect(rect, paint);
    }

    @Override
    public void update(double deltaTime) {

    }

    @Override
    public boolean collide(Scene scene, CharacterObject characterObject) {
        boolean collided = super.collide(scene, characterObject);
        if (collided) {
            health--;
            if (health <= 0)
                scene.remove(this);
            return true;
        }
        return false;
    }
}
