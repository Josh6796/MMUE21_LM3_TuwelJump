package com.example.mmue_lm3;


import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.Log;
import android.view.SurfaceHolder;

import com.example.mmue_lm3.events.CollisionEvent;
import com.example.mmue_lm3.events.EventSystem;
import com.example.mmue_lm3.events.PauseEvent;
import com.example.mmue_lm3.interfaces.Event;
import com.example.mmue_lm3.interfaces.EventListener;
import com.example.mmue_lm3.events.TouchEvent;
import com.example.mmue_lm3.gameobjects.CharacterObject;
import com.example.mmue_lm3.gameobjects.EctsItemObject;
import com.example.mmue_lm3.gameobjects.GameObject;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedDeque;

public class GameLoop implements Runnable, EventListener {

    private static final String TAG = GameLoop.class.getSimpleName();

    public float deltaTime;
    private long lastTime;

    private boolean running;

    private final SurfaceHolder surfaceHolder;
    private final GameSurfaceView gameSurfaceView;

    private final Queue<Event> eventQueue;
    private final Scene gameScene;


    public GameLoop(SurfaceHolder surfaceHolder, GameSurfaceView gameSurfaceView) {
        EventSystem.subscribe(this);
        this.surfaceHolder = surfaceHolder;
        this.gameSurfaceView = gameSurfaceView;

        this.gameScene = new Scene();
        this.eventQueue = new ConcurrentLinkedDeque<>();
    }

    public boolean isRunning() {
        return running;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }

    @Override
    public void run() {
        //One time Updates before first frame update is called
        start();

        setRunning(true);

        while (running) {
            // process events
            events();
            //Update game logic
            update();
            //Render assets
            render();
        }

        shutdown();
    }

    private void start() {
        this.lastTime = System.nanoTime();

        CharacterObject test1 = new CharacterObject(3, 0, 500, 1300);
        GameObject test2 = new EctsItemObject(20, 50, 500);
        GameObject test3 = new EctsItemObject(20, 50, 600);
        gameScene.add(test1);
        gameScene.add(test2);
        gameScene.add(test3);
    }

    private void shutdown() {
        EventSystem.unsubscribe(this);
    }

    private void events() {
        for (Event e = eventQueue.poll(); e != null; e = eventQueue.poll()) {
            processEvent(e);
        }
    }

    private boolean processEvent(Event e) {
        if (e.getClass() == TouchEvent.class)
            return processEvent((TouchEvent) e);
        if (e.getClass() == PauseEvent.class)
            return processEvent((PauseEvent) e);
        if (e.getClass() == CollisionEvent.class)
            return processEvent((CollisionEvent) e);

        return false;
    }

    private boolean processEvent(TouchEvent e) {
        EctsItemObject test = new EctsItemObject(20, e.getX(), e.getY());
        gameScene.add(test);
        Log.d(TAG, "TouchEvent!");

        return true;
    }

    private boolean processEvent(PauseEvent e) {
        Log.d(TAG, "PAUSE!!");
        return true;
    }

    private boolean processEvent(CollisionEvent e) {
        Log.d(TAG, "Collided!");

        // TODO: implement collision
        if(e.getCharacter().getClass() == CharacterObject.class && e.getOther() instanceof GameObject)
        {
            CharacterObject character = (CharacterObject) e.getCharacter();
            GameObject other = (GameObject) e.getOther();

            character.setY( other.getY() - character.getHeight());
        }

        return true;
    }

    private void update() {
        //Calculate time delta for frame independence
        calculateDeltaTime();
        gameScene.update(deltaTime);
    }

    private void render() {
        Canvas canvas = null;
        try {
            canvas = surfaceHolder.lockCanvas();
            synchronized (surfaceHolder) {
                if (canvas == null) return;

                gameSurfaceView.draw(canvas);
                gameScene.draw(canvas);
            }
        } finally {
            if (canvas != null) surfaceHolder.unlockCanvasAndPost(canvas);
        }

    }

    private void calculateDeltaTime() {
        long time = System.nanoTime();
        this.deltaTime = ((time - this.lastTime) / 1e+9f);
        lastTime = time;
    }

    @Override
    public void onEvent(Event event) {
        eventQueue.add(event);
    }
}
