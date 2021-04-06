package com.example.mmue_lm3;

import android.graphics.Canvas;
import android.graphics.Color;

import com.example.mmue_lm3.gameobjects.EctsItemObject;
import com.example.mmue_lm3.gameobjects.GameObject;

import java.util.HashSet;
import java.util.Set;

public class Scene {

    private Set<GameObject> gameObjects;

    public Scene() {
         gameObjects = new HashSet<>();
    }

    public void draw(Canvas canvas) {

        canvas.drawColor(Color.rgb(165, 200, 255));

        for (GameObject gameObject : gameObjects) {
            gameObject.draw(canvas);
        }
    }

    public void add(GameObject object) {
        gameObjects.add(object);
    }

    public void remove(GameObject object) {
        gameObjects.remove(object);
    }

    public void update(float deltaTime) {
        for (GameObject gameObject : gameObjects) {
            gameObject.update(deltaTime);
        }
    }

}
