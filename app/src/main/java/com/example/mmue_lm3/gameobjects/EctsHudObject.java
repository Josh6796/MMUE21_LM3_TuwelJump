 package com.example.mmue_lm3.gameobjects;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

import com.example.mmue_lm3.Camera;

public class EctsHudObject extends HudObject {

    private final Bitmap bitmap;
    private final double x;
    private final double y;

    public EctsHudObject(Bitmap bitmap, double x, double y) {
        super(x, y, bitmap.getWidth(), bitmap.getHeight());

        this.bitmap = bitmap;
        this.x = x;
        this.y = y;
    }

    @Override
    public void draw(Camera camera, Canvas canvas) {
        //canvas.drawBitmap(bitmap, (float) x, (float) y, null);

        Rect targetRect = new Rect((int)x, (int)y, (int)x + bitmap.getWidth() / 6, (int)y + bitmap.getHeight());
        Rect sourceRect = new Rect(0, 0, bitmap.getWidth() / 6, bitmap.getHeight());

        canvas.drawBitmap(bitmap, sourceRect, targetRect, null);
    }

    @Override
    public void update(double deltaTime) {

    }
}
