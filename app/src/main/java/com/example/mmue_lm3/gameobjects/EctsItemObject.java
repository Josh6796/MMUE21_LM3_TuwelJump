/**
 * ECTS Item GameObject
 *
 * @author Joshua Oblong
 */

package com.example.mmue_lm3.gameobjects;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

import com.example.mmue_lm3.Camera;

public class EctsItemObject extends ItemObject {

    private final int ects;

    public EctsItemObject(int ects, double x, double y) {
        super(x, y, 50, 50);

        this.ects = ects;
    }

    @Override
    public void draw(Camera camera, Canvas canvas) {

        Paint paint = new Paint();
        paint.setColor(Color.rgb(255, 0, 0));
        paint.setStyle(Paint.Style.FILL);

        Rect rect = this.getRectangle();
        rect.offset(camera.getX(), camera.getY());
        canvas.drawRect(rect, paint);
    }

    @Override
    public void update(double deltaTime) {
        // x += 1*deltaTime;
    }

    public int getEcts() {
        return ects;
    }
}
