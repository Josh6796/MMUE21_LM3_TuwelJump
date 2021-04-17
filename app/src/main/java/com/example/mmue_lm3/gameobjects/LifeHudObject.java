package com.example.mmue_lm3.gameobjects;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import com.example.mmue_lm3.Camera;

public class LifeHudObject extends HudObject {

    private final Bitmap bitmap;
    private final double x;
    private final double y;

    public LifeHudObject(Bitmap bitmap, double x, double y) {
        super(x, y, bitmap.getWidth(), bitmap.getHeight());

        this.bitmap = bitmap;
        this.x = x;
        this.y = y;
    }

    @Override
    public void draw(Camera camera, Canvas canvas) {
        canvas.drawBitmap(bitmap, (float) x, (float) y, null);
    }

    @Override
    public void update(double deltaTime) {

    }
}
