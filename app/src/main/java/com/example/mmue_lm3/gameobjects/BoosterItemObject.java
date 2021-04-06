package com.example.mmue_lm3.gameobjects;

import android.graphics.Canvas;
import com.example.mmue_lm3.enums.Booster;

public class BoosterItemObject extends ItemObject {

    private final Booster booster;

    public BoosterItemObject(Booster booster, int x, int y) {
        super(x, y, 10, 20);
        this.booster = booster;
    }

    @Override
    public void draw(Canvas canvas) {
        switch (booster) {
            case Speed:
                break;
            case Invicibility:
                break;
            case SlowTime:
                break;
            case Damage:
                break;
        }
    }

    public Booster getBooster() {
        return booster;
    }

    @Override
    public void update(float deltaTime) {

    }
}
