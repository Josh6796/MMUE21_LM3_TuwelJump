package com.example.mmue_lm3.gameobjects;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.util.Log;

import com.example.mmue_lm3.Camera;
import com.example.mmue_lm3.Scene;
import com.example.mmue_lm3.events.BoosterEvent;
import com.example.mmue_lm3.events.ECTSEvent;
import com.example.mmue_lm3.events.EventSystem;

import static java.lang.Math.max;

/**
 * Character GameObject
 *
 * @author Joshua Oblong
 */
public class CharacterObject extends GameObject {

    private static final String TAG = CharacterObject.class.getSimpleName();

    private static final int MAX_HEALTH = 3;
    private static final int PRIORITY = 3;
    public static final double MAX_VELOCITY = 500;

    private final Sprite sprite;

    private int health;
    private int ects;

    private double verticalVelocity;
    private double horizontalCenter;
    private double lastY;
    private double lastX;
    private double highestPlatform;

    public CharacterObject(Bitmap bitmap, int health, int ects, int x, int y) {
        super(x, y, 0, 0, PRIORITY);
        this.lastY = y;
        this.health = health;
        this.ects = ects;
        this.verticalVelocity = -150;
        this.highestPlatform = y;

        this.sprite = new Sprite(bitmap, x, y, 2, 600);

        super.setWidth(sprite.getFrameWidth());
        super.setHeight(sprite.getFrameHeight());
    }

    @Override
    public void draw(Camera camera, Canvas canvas) {
        this.sprite.draw(camera, canvas);

        // ECTS HUD
        Paint paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setTextSize(70);
        paint.setTypeface(Typeface.DEFAULT_BOLD);

        canvas.drawText("" + ects, 150, 275, paint);
    }

    @Override
    public void update(double deltaTime) {

        lastY = y;
        lastX = x;

        // horizontal velocity
        double deltaX = horizontalCenter - (x + width / 2.0);
        //this.x += deltaX * 0.8 * deltaTime;
        this.x += deltaX;
        this.sprite.setX(this.x);

        // vertical velocity
        // TODO: improve (update verticalVelocity, ...)
        this.y -= verticalVelocity * deltaTime;
        this.sprite.setY(this.y);
        verticalVelocity -= 600.0 * deltaTime;
        verticalVelocity = max(verticalVelocity, -250);

        sprite.update(System.currentTimeMillis());
    }

    public void jump(Scene scene) {
        // TODO: improve
        if (highestPlatform > y) {
            scene.moveCamera(0, - (highestPlatform - y));
            highestPlatform = y;
        }

        verticalVelocity = MAX_VELOCITY;
    }

    public void setVerticalVelocity(double velocity) {
        this.verticalVelocity = velocity;
    }

    public void consume(EctsItemObject ectsItem) {
        this.ects += ectsItem.getEcts();
        EventSystem.onEvent(new ECTSEvent(this.ects));
    }

    public void consume(BoosterItemObject boosterItem) {
        Log.w(TAG, "Booster Item consumed!!!"); // TODO: do something (useful)...
        EventSystem.onEvent(new BoosterEvent(boosterItem.getBooster()));
    }

    public void setHorizontalCenter(double center) {
        this.horizontalCenter = center;
    }

    public void move(double x, double y) {
        lastY = y;
        lastX = x;

        this.x += x;
        this.y += y;

        this.sprite.setX(this.x);
        this.sprite.setY(this.y);
    }

    public double lastBottom() {
        return lastY + height;
    }

    public double lastTop() {
        return lastY;
    }

    public double lastLeft() {
        return lastX;
    }

    public double lastRight() {
        return lastX + width;
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
