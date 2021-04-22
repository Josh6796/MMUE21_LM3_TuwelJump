package com.example.mmue_lm3.hud;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

public class LifeElement extends HudElement {

    private final Bitmap bitmap;
    private final int width;
    private final int height;

    public LifeElement(double x, double y, Bitmap bitmap, int width, int height) {
        super(x, y);

        this.bitmap = bitmap;
        this.width = width;
        this.height = height;
    }

    @Override
    public void draw(Canvas canvas) {
        Rect sourceRect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        Rect targetRect = new Rect((int) x, (int) y, (int) x + width, (int) y + height);
        canvas.drawBitmap(bitmap, sourceRect, targetRect, null);
    }
}
