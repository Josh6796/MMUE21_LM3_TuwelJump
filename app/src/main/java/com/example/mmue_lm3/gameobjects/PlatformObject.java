/**
 * Platform GameObject
 *
 * @author Joshua Oblong
 */

package com.example.mmue_lm3.gameobjects;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

import com.example.mmue_lm3.Camera;

public class PlatformObject extends GameObject {

    private static final int PRIORITY = 1;

    public PlatformObject(double x, double y, int width) {
        super(x, y, width, 50, PRIORITY);
    }

    @Override
    public void draw(Camera camera, Canvas canvas) {
        Paint paint = new Paint();
        paint.setColor(Color.rgb(153, 18, 51));
        paint.setStyle(Paint.Style.FILL);

        Rect rect = this.getRectangle();
        rect.offset(camera.getX(), camera.getY());
        canvas.drawRect(rect, paint);
    }

    @Override
    public void update(double deltaTime) {

    }
}
