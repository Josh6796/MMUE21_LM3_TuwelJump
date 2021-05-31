package com.example.mmue_lm3.gameobjects;

import android.graphics.Canvas;

import com.example.mmue_lm3.Camera;
import com.example.mmue_lm3.Scene;
import com.example.mmue_lm3.enums.Booster;
import com.example.mmue_lm3.events.BoosterEvent;
import com.example.mmue_lm3.events.ECTSEvent;
import com.example.mmue_lm3.events.EventSystem;
import com.example.mmue_lm3.events.HealthEvent;
import com.example.mmue_lm3.events.LoseEvent;
import com.example.mmue_lm3.events.WinEvent;
import com.example.mmue_lm3.sprites.DynamicBitmap;
import com.example.mmue_lm3.sprites.EventAnimatedSprite;

import static java.lang.Math.max;

/**
 * Character GameObject
 *
 * @author Joshua Oblong
 */
public class CharacterObject extends GameObject {

    private static final String TAG = CharacterObject.class.getSimpleName();

    private static final int MAX_HEALTH = 3;
    private static final int PRIORITY = 4;
    public static final double MAX_VELOCITY = 550;

    private final EventAnimatedSprite sprite;

    private final int level;
    private int health;
    private int ects;

    // Booster
    private double speedBoost;
    private double slowMotionBoost;
    private double damageBoost;
    private double invincibilityBoost;

    private double verticalVelocity;
    private double horizontalCenter;
    private double lastY;
    private double lastX;
    private double highestPlatform;

    public CharacterObject(DynamicBitmap bitmap, int level, int health, int ects, int x, int y) {
        super(x, y, 0, 0, PRIORITY);
        this.sprite = new EventAnimatedSprite(bitmap, 2, 0, 1, false);
        this.lastY = y;

        this.level = level;
        this.health = health;
        this.ects = ects;

        this.verticalVelocity = -150;
        this.highestPlatform = y;

        slowMotionBoost = 0;
        damageBoost = 0;
        invincibilityBoost = 0;

        super.setWidth(sprite.getWidth());
        super.setHeight(sprite.getHeight());
    }

    @Override
    public void draw(Camera camera, Canvas canvas) {
        sprite.draw(canvas, getScreenX(camera), getScreenY(camera), width, height);

    }

    @Override
    public void update(double deltaTime) {
        lastY = worldY;
        lastX = worldX;

        // horizontal velocity
        double deltaX = horizontalCenter - (worldX + width / 2.0);
        //this.x += deltaX * 0.8 * deltaTime;
        this.worldX += deltaX;

        // vertical velocity
        this.worldY -= verticalVelocity * deltaTime;
        verticalVelocity -= 600.0 * deltaTime;
        verticalVelocity = max(verticalVelocity, -350);

        if (verticalVelocity < 0)
            sprite.reset();

        // Booster
        if (isActive(Booster.SlowMotion)) {
            slowMotionBoost -= deltaTime;
            if (!isActive(Booster.SlowMotion))
                EventSystem.onEvent(new BoosterEvent(Booster.SlowMotion, false));
        }

        if (isActive(Booster.Damage)) {
            damageBoost -= deltaTime;
            if (!isActive(Booster.Damage))
                EventSystem.onEvent(new BoosterEvent(Booster.Damage, false));
        }

        if (isActive(Booster.Invincibility)) {
            invincibilityBoost -= deltaTime;
            if (!isActive(Booster.Invincibility))
                EventSystem.onEvent(new BoosterEvent(Booster.Invincibility, false));
        }

        if (isActive(Booster.Speed)) {
            speedBoost -= deltaTime;
            if (!isActive(Booster.Speed))
                EventSystem.onEvent(new BoosterEvent(Booster.Speed, false));
        }

    }

    public void jump(Scene scene) {
        if (highestPlatform > worldY) {
            scene.moveCamera(0, -(highestPlatform - worldY));
            highestPlatform = worldY;
        }

        sprite.update();
        verticalVelocity = isActive(Booster.Speed) ? MAX_VELOCITY * 1.3 : MAX_VELOCITY;
    }

    public void setVerticalVelocity(double velocity) {
        this.verticalVelocity = velocity;
    }

    public void consume(EctsItemObject ectsItem) {
        int ects = ectsItem.getEcts();
        addEcts(ects);
    }

    public void consume(BoosterItemObject boosterItem) {

        switch (boosterItem.getBooster()) {
            case SlowMotion:
                this.slowMotionBoost = 6;
                break;
            case Damage:
                this.damageBoost = 15;
                break;
            case Invincibility:
                this.invincibilityBoost = 12;
                break;
            case Speed:
                this.speedBoost = 10;
                break;
        }
        EventSystem.onEvent(new BoosterEvent(boosterItem.getBooster(), true));
    }

    public void setHorizontalCenter(double center) {
        this.horizontalCenter = center;
    }

    public void move(double x, double y) {
        lastY = y;
        lastX = x;

        this.worldX += x;
        this.worldY += y;
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

        switch (level) {
            case 0:
                if (this.ects >= 180)
                    EventSystem.onEvent(new WinEvent(this.level, this.health, this.ects));
                break;
            case 1:
                if (this.ects >= 300)
                    EventSystem.onEvent(new WinEvent(this.level, this.health, this.ects));
                break;
        }
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
                if (health == 0) {
                    EventSystem.onEvent(new LoseEvent(ects));
                }
            }
        }
    }

    public boolean isActive(Booster booster) {
        switch (booster) {
            case Speed:
                return speedBoost > 0;
            case Invincibility:
                return invincibilityBoost > 0;
            case Damage:
                return damageBoost > 0;
            case SlowMotion:
                return slowMotionBoost > 0;
        }
        return false;
    }
}
