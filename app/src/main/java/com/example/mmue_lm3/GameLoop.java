package com.example.mmue_lm3;


import android.graphics.Canvas;
import android.view.SurfaceHolder;

import com.example.mmue_lm3.interfaces.Event;
import com.example.mmue_lm3.interfaces.EventListener;
import com.example.mmue_lm3.events.TouchEvent;
import com.example.mmue_lm3.gameobjects.EctsItemObject;

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
    }

    private void start() {
        this.lastTime = System.nanoTime();
    }

    private void events() {
        for (Event e = eventQueue.poll(); e != null; e = eventQueue.poll()) {
            processEvent(e);
        }
    }

    private boolean processEvent(Event e) {
        if (e.getClass() == TouchEvent.class)
            return processEvent((TouchEvent) e);

        return false;
    }

    private boolean processEvent(TouchEvent e) {
        EctsItemObject test = new EctsItemObject(20, e.getX(), e.getY());
        gameScene.add(test);
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
