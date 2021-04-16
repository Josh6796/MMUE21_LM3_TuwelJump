

package com.example.mmue_lm3.gameobjects;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

import com.example.mmue_lm3.Camera;
import com.example.mmue_lm3.R;
import com.example.mmue_lm3.Scene;

/**
 * ECTS Item GameObject
 *
 * @author Joshua Oblong
 */
public class EctsItemObject extends ItemObject {

    private final int ects;
    private final Sprite sprite;

    public EctsItemObject(Context context, int ects, double x, double y) {
        super(x, y, 50, 50);

        this.ects = ects;

        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.ects);
        this.sprite = new Sprite(bitmap, x, y, 6, 50);
    }

    @Override
    public void draw(Camera camera, Canvas canvas) {
        sprite.draw(camera, canvas);
    }

    @Override
    public void update(double deltaTime) {
        sprite.update(System.currentTimeMillis());
    }

    public int getEcts() {
        return ects;
    }

    @Override
    public boolean collide(Scene scene, CharacterObject character) {
        character.consume(this);
        scene.remove(this);
        return true;
    }
}
