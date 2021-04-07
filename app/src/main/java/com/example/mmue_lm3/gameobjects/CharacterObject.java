package com.example.mmue_lm3.gameobjects;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class CharacterObject extends GameObject {

    private static final int MAX_HEALTH = 3;
    private static final int PRIORITY = 3;

    private int health;
    private int ects;

    public CharacterObject(int health, int ects, int x, int y) {
        super(x, y, 50, 100, PRIORITY);
        this.health = health;
        this.ects = ects;
    }

    @Override
    public void draw(Canvas canvas) {
        Paint paint = new Paint();
        paint.setColor(Color.rgb(0, 255, 0));
        paint.setStyle(Paint.Style.FILL);
        canvas.drawRect(this.getRectangle(), paint);
    }

    @Override
    public void update(float deltaTime) {

    }

    public int getHealth() {
        return health;
    }

    public int getEcts() {
        return ects;
    }

    public void addEcts(int ects) {
        this.ects += ects;
    }

    public void addHealth(int health) {
        if (this.health + health <= MAX_HEALTH) {
            if (this.health + health >= 0) {
                this.health += health;
            } else {
                this.health = 0;
            }
        } else {
            this.health = MAX_HEALTH;
        }
    }
}
