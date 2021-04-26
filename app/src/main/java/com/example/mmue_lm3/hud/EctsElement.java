package com.example.mmue_lm3.hud;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;

public class EctsElement extends HudElement {

    private final Bitmap bitmap;
    private final int width;
    private final int height;
    private int ects;

    public EctsElement(double x, double y, Bitmap bitmap, int width, int height) {
        super(x, y);

        this.bitmap = bitmap;
        this.width = width;
        this.height = height;
        ects = 0;
    }

    public void add(int ects) {
        this.ects += ects;
    }

    @Override
    public void draw(Canvas canvas) {
        // icon
        Rect sourceRect = new Rect(0, 0, bitmap.getWidth() / 6, bitmap.getHeight());
        Rect targetRect = new Rect((int) x, (int) y, (int) x + width, (int) y + height);
        canvas.drawBitmap(bitmap, sourceRect, targetRect, null);

        // ects count
        Paint paint = new Paint();
        paint.setTextSize(70);
        paint.setTypeface(Typeface.DEFAULT_BOLD);
        canvas.drawText(Integer.toString(ects), (int) x + width + 5, (int) y + height, paint);
    }
}
