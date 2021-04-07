package com.example.mmue_lm3.gameobjects;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class EctsItemObject extends ItemObject {

    private final int ects;

    public EctsItemObject(int ects, int x, int y) {
        super(x, y, 100, 20);

        this.ects = ects;
    }

    @Override
    public void draw(Canvas canvas) {

        Paint paint = new Paint();
        paint.setColor(Color.rgb(255, 0, 0));
        paint.setStyle(Paint.Style.FILL);
        canvas.drawCircle(x, y, width / 2.0f, paint);
    }

    @Override
    public void update(float deltaTime) {
        // x += 1*deltaTime;
    }

    public int getEcts() {
        return ects;
    }
}
