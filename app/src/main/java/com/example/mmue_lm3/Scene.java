package com.example.mmue_lm3;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;

import com.example.mmue_lm3.gameobjects.CharacterObject;
import com.example.mmue_lm3.gameobjects.GameObject;
import com.example.mmue_lm3.interfaces.Collidable;

import java.util.Set;
import java.util.Stack;
import java.util.TreeSet;

/**
 * Class that implements the Scene
 *
 * @author Joshua Oblong
 */
public class Scene {

    private static final String TAG = Scene.class.getSimpleName();
    private final Set<GameObject> gameObjects;
    private final Stack<GameObject> trash;

    private CharacterObject character;
    private final Camera camera;

    public Scene(int width, int height) {
        gameObjects = new TreeSet<>();
        trash = new Stack<>();
        camera = new Camera(0, 0, width, height);
    }

    public void draw(Canvas canvas) {
        // background
        canvas.drawColor(Color.rgb(165, 200, 255));

        for (GameObject gameObject : gameObjects) {
            gameObject.draw(camera, canvas);
        }
    }

    public void moveCamera(double x, double y) {
        camera.move(x, y);
    }

    public void add(GameObject object) {
        gameObjects.add(object);
    }

    public void add(CharacterObject object) {
        character = object;
        gameObjects.add(character);
        character.setHorizontalCenter(camera.getCenterX());
    }

    public void remove(GameObject object) {
        trash.add(object);
    }

    public void update(double deltaTime) {
        gameObjects.removeAll(trash);
        trash.clear();

        camera.update(deltaTime);
        character.setHorizontalCenter(camera.getCenterX());

        for (GameObject gameObject : gameObjects) {
            gameObject.update(deltaTime);

            if (!gameObject.equals(character) && gameObject instanceof Collidable && Rect.intersects(character.getRectangle(), gameObject.getRectangle())) {
                Collidable collidable = (Collidable) gameObject;
                collidable.collide(this, character);
            }
        }
    }

}
