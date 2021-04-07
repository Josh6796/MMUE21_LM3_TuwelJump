package com.example.mmue_lm3.gameobjects;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

import com.example.mmue_lm3.Camera;

import static java.lang.Math.max;

public class CharacterObject extends GameObject {

    private static final int MAX_HEALTH = 3;
    private static final int PRIORITY = 3;

    private int health;
    private int ects;

    private double verticalVelocity;

    public CharacterObject(int health, int ects, int x, int y) {
        super(x, y, 50, 100, PRIORITY);
        this.health = health;
        this.ects = ects;
        this.verticalVelocity = -150;
    }

    @Override
    public void draw(Camera camera, Canvas canvas) {
        Paint paint = new Paint();
        paint.setColor(Color.rgb(0, 255, 0));
        paint.setStyle(Paint.Style.FILL);

        Rect rect = this.getRectangle();
        rect.offset(camera.getX(), camera.getY());
        canvas.drawRect(rect, paint);
    }

    @Override
    public void update(double deltaTime) {
        // TODO: improve (update verticalVelocity, ...)
        y -= verticalVelocity * deltaTime;
        verticalVelocity -= 600.0 * deltaTime;
        verticalVelocity = max(verticalVelocity, -250);
    }

    public void jump() {
        // TODO: improve
        verticalVelocity = 500;
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
