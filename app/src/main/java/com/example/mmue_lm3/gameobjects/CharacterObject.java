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
import com.example.mmue_lm3.events.HealthEvent;

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

        this.sprite = new Sprite(bitmap, 2, .5, x, y);

        super.setWidth(sprite.getFrameWidth());
        super.setHeight(sprite.getFrameHeight());
    }

    @Override
    public void draw(Camera camera, Canvas canvas) {
        this.sprite.draw(camera, canvas);
    }

    @Override
    public void update(double deltaTime) {

        lastY = y;
        lastX = x;

        // horizontal velocity
        double deltaX = horizontalCenter - (x + width / 2.0);
        //this.x += deltaX * 0.8 * deltaTime;
        this.x += deltaX;

        // vertical velocity
        // TODO: improve (update verticalVelocity, ...)
        this.y -= verticalVelocity * deltaTime;
        verticalVelocity -= 600.0 * deltaTime;
        verticalVelocity = max(verticalVelocity, -250);

        sprite.setWorldPos(x, y);
        sprite.update(deltaTime);
    }

    public void jump(Scene scene) {
        // TODO: improve
        if (highestPlatform > y) {
            scene.moveCamera(0, -(highestPlatform - y));
            highestPlatform = y;
        }

        verticalVelocity = MAX_VELOCITY;
    }

    public void setVerticalVelocity(double velocity) {
        this.verticalVelocity = velocity;
    }

    public void consume(EctsItemObject ectsItem) {
        int ects = ectsItem.getEcts();
        this.ects += ects;
        EventSystem.onEvent(new ECTSEvent(ects));
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

        sprite.setWorldPos(this.x, this.y);
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
        EventSystem.onEvent(new ECTSEvent(ects));
    }

    public void addHealth(boolean add) {
        if (add) {
            if (health < MAX_HEALTH) {
                health++;
                EventSystem.onEvent(new HealthEvent(add));
            }
        } else {
            if (health > 0) {
                health--;
                EventSystem.onEvent(new HealthEvent(add));
            }
        }
    }
}
