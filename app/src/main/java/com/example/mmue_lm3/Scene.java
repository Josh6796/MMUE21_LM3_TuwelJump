package com.example.mmue_lm3;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;

import com.example.mmue_lm3.events.CollisionEvent;
import com.example.mmue_lm3.events.EventSystem;
import com.example.mmue_lm3.gameobjects.CharacterObject;
import com.example.mmue_lm3.gameobjects.GameObject;

import java.util.Set;
import java.util.TreeSet;

public class Scene {

    private static final String TAG = Scene.class.getSimpleName();
    private final Set<GameObject> gameObjects;
    private CharacterObject character;

    public Scene() {
         gameObjects = new TreeSet<>();

    }

    public void draw(Canvas canvas) {

        // background
        canvas.drawColor(Color.rgb(165, 200, 255));

        for (GameObject gameObject : gameObjects) {
            gameObject.draw(canvas);
        }
    }

    public void add(GameObject object) {
        gameObjects.add(object);
    }

    public void add(CharacterObject object) {
        character = object;
        gameObjects.add(character);
    }

    public void remove(GameObject object) {
        gameObjects.remove(object);
    }

    public void update(float deltaTime) {
        for (GameObject gameObject : gameObjects) {
            gameObject.update(deltaTime);

            if (!gameObject.equals(character) && Rect.intersects(character.getRectangle(), gameObject.getRectangle())) {
                EventSystem.onEvent(new CollisionEvent(character, gameObject));
            }
        }
    }

    public Set<GameObject> getGameObjects() {
        return gameObjects;
    }
}
