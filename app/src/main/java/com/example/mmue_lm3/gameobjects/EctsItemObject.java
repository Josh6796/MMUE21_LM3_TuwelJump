package com.example.mmue_lm3.gameobjects;

import android.graphics.Canvas;

public class EctsItemObject extends ItemObject {

    private final int ects;

    public EctsItemObject(int ects) {
        this.ects = ects;
    }

    @Override
    public void draw(Canvas canvas) {

    }

    public int getEcts() {
        return ects;
    }
}
