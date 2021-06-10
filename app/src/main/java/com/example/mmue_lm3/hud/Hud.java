package com.example.mmue_lm3.hud;

import android.graphics.Canvas;
import android.util.Log;

import com.example.mmue_lm3.GameSurfaceView;
import com.example.mmue_lm3.enums.Booster;
import com.example.mmue_lm3.sprites.Sprite;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.Stack;

/**
 * Represents a head-up display (HUD) for the {@link GameSurfaceView}. Displays {@link LifeElement},
 * {@link LifeElement} and other {@link HudElement}.
 *
 * @author Mathias Schwengerer
 */
public class Hud {
    private static final String TAG = Hud.class.getSimpleName();

    public static final int MAX_LIFE = 3;

    private final Set<HudElement> elements;
    private final Stack<LifeElement> lives;

    private final Queue<BoosterElement> booster;
    private final Map<Booster, BoosterElement> activeBooster;

    private EctsElement ects;

    private final Sprite lifeSprite;
    private final Sprite ectsSprite;
    private final Sprite slowMotion;
    private final Sprite speed;
    private final Sprite damage;
    private final Sprite invincibility;

    private int width;
    private int height;

    public Hud(Sprite lifeSprite, Sprite ectsSprite, Sprite slowMotion, Sprite speed, Sprite damage, Sprite invincibility, int width, int height) {
        this.lifeSprite = lifeSprite;
        this.ectsSprite = ectsSprite;
        this.slowMotion = slowMotion;
        this.speed = speed;
        this.damage = damage;
        this.invincibility = invincibility;

        this.width = width;
        this.height = height;

        elements = new HashSet<>();
        lives = new Stack<>();
        booster = new LinkedList<>();
        activeBooster = new HashMap<>();

        initEcts();
    }

    public void add(HudElement element) {
        elements.add(element);
    }

    public void remove(HudElement element) {
        elements.remove(element);
    }

    public boolean addLife() {
        if (lives.size() >= MAX_LIFE) {
            Log.w(TAG, "Unable to add life. MAX_LIFE reached!");
            return false;
        }

        pushLife();
        return true;
    }

    public boolean removeLife() {
        if (lives.empty()) {
            Log.w(TAG, "Unable to remove life. No life displayed!");
            return false;
        }

        lives.pop();
        return true;
    }

    public void addEcts(int ects) {
        this.ects.add(ects);
    }

    public void booster(Booster booster, boolean active) {
        if ((activeBooster.get(booster) != null) == active)
            return;

        if (active) {
            BoosterElement element = createBooster(booster);
            this.booster.add(element);
            this.activeBooster.put(booster, element);
        } else {
            this.booster.remove(activeBooster.get(booster));
            this.activeBooster.remove(booster);
        }
    }

    private BoosterElement createBooster(Booster booster) {
        int x = 300;
        int y = 10;
        int width;

        switch (booster) {
            case SlowMotion:
                width =  (int) ((double) slowMotion.getWidth() / (double) slowMotion.getHeight() * 70.0);
                return new BoosterElement(slowMotion, x, y, width, 70);
            case Speed:
                width =  (int) ((double) speed.getWidth() / (double) speed.getHeight() * 70.0);
                return new BoosterElement(speed, x, y, width, 70);
            case Damage:
                width =  (int) ((double) damage.getWidth() / (double) damage.getHeight() * 70.0);
                return new BoosterElement(damage, x, y, width, 70);
            case Invincibility:
                width =  (int) ((double) invincibility.getWidth() / (double) invincibility.getHeight() * 70.0);
                return new BoosterElement(invincibility, x, y, width, 70);
        }

        return null;
    }

    public void draw(Canvas canvas) {
        drawLife(canvas);
        drawEcts(canvas);
        drawBooster(canvas);
        drawElements(canvas);
    }

    private void pushLife() {
        int offsetX = 10;
        int offsetY = 10;

        LifeElement life = new LifeElement(lifeSprite, offsetX + (50 + offsetX) * lives.size(), offsetY, 50, 50);
        lives.push(life);
    }

    private void initEcts() {
        int offsetX = 10;
        int offsetY = 70;

        ects = new EctsElement(ectsSprite, offsetX, offsetY, 45, 50);
    }

    private void drawLife(Canvas canvas) {
        for (LifeElement life : lives)
            life.draw(canvas);
    }

    private void drawEcts(Canvas canvas) {
        if (ects != null)
            ects.draw(canvas);
    }

    private void drawElements(Canvas canvas) {
        for (HudElement element : elements)
            element.draw(canvas);
    }

    private void drawBooster(Canvas canvas) {
        int offset = 5;
        int xOffset = 0;
        for (BoosterElement boost : booster) {
            boost.draw(canvas, xOffset);
            xOffset += boost.getWidth() + offset;
        }
    }

}
