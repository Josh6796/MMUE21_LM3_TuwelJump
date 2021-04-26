package com.example.mmue_lm3.hud;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;

import com.example.mmue_lm3.gameobjects.Sprite;

public class EctsElement extends HudElement {

    private int ects;

    public EctsElement(Sprite sprite, int screenX, int screenY, int width, int height) {
        super(sprite, screenX, screenY, width, height);

        ects = 0;
    }

    public void add(int ects) {
        this.ects += ects;
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);

        // ects count
        Paint paint = new Paint();
        paint.setTextSize(70);
        paint.setTypeface(Typeface.DEFAULT_BOLD);
        canvas.drawText(Integer.toString(ects), screenX + width + 5, screenY + height, paint);
    }
}
