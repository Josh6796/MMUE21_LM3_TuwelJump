package com.example.mmue_lm3.gameobjects;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

import com.example.mmue_lm3.util.Camera;
import com.example.mmue_lm3.Scene;
import com.example.mmue_lm3.interfaces.Collidable;

/**
 * Platform GameObject which is there for creating, drawing, updating an detecting collision.
 *
 * @author Joshua Oblong
 */
public class PlatformObject extends GameObject implements Collidable {

    private static final int PRIORITY = 1;

    public PlatformObject(double x, double y, int width) {
        super(x, y, width, 45, PRIORITY);
    }

    @Override
    public void draw(Camera camera, Canvas canvas) {
        Paint paint = new Paint();
        paint.setColor(Color.rgb(153, 18, 51));
        paint.setStyle(Paint.Style.FILL);

        Rect rect = this.getRectangle();
        rect.offset(-(int)camera.getX(), -(int)camera.getY());
        canvas.drawRect(rect, paint);
    }

    @Override
    public void update(double deltaTime) {

    }

    @Override
    public boolean collide(Scene scene, CharacterObject character) {
        if (character.lastBottom() - 1 <= this.top() && character.bottom() >= this.top()) {
            character.jump(scene);
            return true;
        }
        return false;
    }
}
