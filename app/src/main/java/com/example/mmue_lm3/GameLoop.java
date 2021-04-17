package com.example.mmue_lm3;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.Log;
import android.view.SurfaceHolder;

import com.example.mmue_lm3.enums.Booster;
import com.example.mmue_lm3.events.EventSystem;
import com.example.mmue_lm3.events.PauseEvent;
import com.example.mmue_lm3.events.VelocityEvent;
import com.example.mmue_lm3.gameobjects.BoosterItemObject;
import com.example.mmue_lm3.gameobjects.DestroyablePlatformObject;
import com.example.mmue_lm3.gameobjects.EctsHudObject;
import com.example.mmue_lm3.gameobjects.LifeHudObject;
import com.example.mmue_lm3.gameobjects.PlatformObject;
import com.example.mmue_lm3.gameobjects.ProfessorObject;
import com.example.mmue_lm3.interfaces.Event;
import com.example.mmue_lm3.interfaces.EventListener;
import com.example.mmue_lm3.events.TouchEvent;
import com.example.mmue_lm3.gameobjects.CharacterObject;
import com.example.mmue_lm3.gameobjects.EctsItemObject;
import com.example.mmue_lm3.gameobjects.GameObject;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedDeque;

/**
 * Class for the GameLoop
 *
 * @author Joshua Oblong (Demo as Template)
 */
public class GameLoop implements Runnable, EventListener {

    private static final String TAG = GameLoop.class.getSimpleName();

    public double deltaTime;
    private long lastTime;

    private boolean running;

    private final SurfaceHolder surfaceHolder;
    private final GameSurfaceView gameSurfaceView;

    private final Queue<Event> eventQueue;
    private final Scene gameScene;

    // Bitmaps
    private Bitmap characterBitmap;
    private Bitmap professorBitmap;
    private Bitmap coffeeBitmap;
    private Bitmap klubnateBitmap;
    private Bitmap clockBitmap;
    private Bitmap mathbookBitmap;
    private Bitmap ectsBitmap;
    private Bitmap heartBitmap;


    public GameLoop(SurfaceHolder surfaceHolder, GameSurfaceView gameSurfaceView) {
        EventSystem.subscribe(this);
        this.surfaceHolder = surfaceHolder;
        this.gameSurfaceView = gameSurfaceView;

        this.gameScene = new Scene(gameSurfaceView.getWidth(), gameSurfaceView.getHeight());
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

        Context context = this.gameSurfaceView.getContext();

        characterBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.character);
        professorBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.professor);
        coffeeBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.coffee);
        klubnateBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.klubnate);
        clockBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.clock);
        mathbookBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.mathbook);
        ectsBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.ects);
        heartBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.heart);

        this.initScene(gameScene);
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

        if (e.getClass() == VelocityEvent.class)
            return processEvent((VelocityEvent) e);

        return false;
    }

    private boolean processEvent(TouchEvent e) {
        Log.d(TAG, "TouchEvent!");

        return true;
    }

    private boolean processEvent(PauseEvent e) {
        Log.d(TAG, "PAUSE!!");
        return true;
    }

    private boolean processEvent(VelocityEvent e) {
        gameScene.moveCamera(-(int) e.getX(), 0);
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

    // TODO: remove (just for testing)
    void initScene(Scene scene) {


        // Character
        CharacterObject character = new CharacterObject(characterBitmap,3, 0, 500, 1300);
        scene.add(character);

        // Lives
        for (int i = 0; i < character.getHealth(); i++) {
            LifeHudObject life = new LifeHudObject(heartBitmap, 50 + 100*i, 50);
            scene.add(life);
        }

        // ECTS HUD
        GameObject ectsHud = new EctsHudObject(ectsBitmap,50, 200);
        scene.add(ectsHud);

        // Booster
        GameObject booster_1 = new BoosterItemObject(klubnateBitmap, Booster.Speed, 300, 1300);
        GameObject booster_2 = new BoosterItemObject(coffeeBitmap, Booster.Damage, 600, 1300);
        GameObject booster_3 = new BoosterItemObject(clockBitmap, Booster.SlowMotion, 200, 1500);
        GameObject booster_4 = new BoosterItemObject(mathbookBitmap, Booster.Invincibility, 700, 1500);
        scene.add(booster_1);
        scene.add(booster_2);
        scene.add(booster_3);
        scene.add(booster_4);

        // Items
        GameObject ects_1 = new EctsItemObject(ectsBitmap, 20, 50, 1200);
        GameObject ects_2 = new EctsItemObject(ectsBitmap, 20, 50, 600);
        scene.add(ects_1);
        scene.add(ects_2);

        // Platforms
        GameObject platform_1 = new PlatformObject(50, 200, 100);
        GameObject platform_2 = new PlatformObject(1000, 400, 200);
        GameObject platform_3 = new PlatformObject(500, 500, 100);
        GameObject platform_4 = new PlatformObject(800, 900, 150);
        GameObject platform_5 = new PlatformObject(300, 1450, 500);
        GameObject platform_6 = new PlatformObject(0, 1650, 1000);
        GameObject platform_7 = new DestroyablePlatformObject(700, 1300, 200, 2);
        GameObject platform_8 = new DestroyablePlatformObject(550, 1120, 200, 1);
        GameObject platform_9 = new DestroyablePlatformObject(150, 1050, 250, 1);
        scene.add(platform_1);
        scene.add(platform_2);
        scene.add(platform_3);
        scene.add(platform_4);
        scene.add(platform_5);
        scene.add(platform_6);
        scene.add(platform_7);
        scene.add(platform_8);
        scene.add(platform_9);

        // Professor
        ProfessorObject prof_1 = new ProfessorObject(professorBitmap,5, 6, 920, 720);
        ProfessorObject prof_2 = new ProfessorObject(professorBitmap,5, 6, 700, 590);
        scene.add(prof_1);
        scene.add(prof_2);

    }
}
