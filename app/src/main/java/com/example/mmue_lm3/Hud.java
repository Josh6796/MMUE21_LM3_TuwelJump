package com.example.mmue_lm3;

import android.graphics.Canvas;
import android.util.Log;

import com.example.mmue_lm3.sprites.Sprite;
import com.example.mmue_lm3.hud.EctsElement;
import com.example.mmue_lm3.hud.HudElement;
import com.example.mmue_lm3.hud.LifeElement;

import java.util.Deque;
import java.util.HashSet;
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
    private EctsElement ects;

    private final Sprite lifeSprite;
    private final Sprite ectsSprite;

    private int width;
    private int height;

    public Hud(Sprite lifeSprite, Sprite ectsSprite, int width, int height) {
        this.lifeSprite = lifeSprite;
        this.ectsSprite = ectsSprite;

        this.width = width;
        this.height = height;

        elements = new HashSet<>();
        lives = new Stack<>();

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

    public void draw(Canvas canvas) {
        drawLife(canvas);
        drawEcts(canvas);
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

}
