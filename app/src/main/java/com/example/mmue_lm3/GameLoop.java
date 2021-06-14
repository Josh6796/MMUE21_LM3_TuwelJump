package com.example.mmue_lm3;


import android.content.res.Resources;
import android.graphics.Canvas;
import android.util.Log;
import android.view.SurfaceHolder;

import com.example.mmue_lm3.enums.Booster;
import com.example.mmue_lm3.events.BoosterEvent;
import com.example.mmue_lm3.events.ECTSEvent;
import com.example.mmue_lm3.events.EventSystem;
import com.example.mmue_lm3.events.HealthEvent;
import com.example.mmue_lm3.events.LevelEvent;
import com.example.mmue_lm3.events.PauseEvent;
import com.example.mmue_lm3.events.ResumeEvent;
import com.example.mmue_lm3.events.TouchEvent;
import com.example.mmue_lm3.events.VelocityEvent;
import com.example.mmue_lm3.gameobjects.BoosterItemObject;
import com.example.mmue_lm3.gameobjects.CharacterObject;
import com.example.mmue_lm3.gameobjects.DestroyablePlatformObject;
import com.example.mmue_lm3.gameobjects.EctsItemObject;
import com.example.mmue_lm3.gameobjects.GameObject;
import com.example.mmue_lm3.gameobjects.PlatformObject;
import com.example.mmue_lm3.gameobjects.ProfessorObject;
import com.example.mmue_lm3.hud.Hud;
import com.example.mmue_lm3.interfaces.Event;
import com.example.mmue_lm3.interfaces.EventListener;
import com.example.mmue_lm3.sprites.DynamicBitmap;
import com.example.mmue_lm3.sprites.Sprite;

import java.util.Queue;
import java.util.Random;
import java.util.concurrent.ConcurrentLinkedDeque;

/**
 * Class for the GameLoop. Here's where the magic happens. Not only do we load all the assets here
 * but it also processes the different events and most importantly initializes and holds the render
 * loop which is for continuously drawing something on the canvas until we want it to stop.
 *
 * @author Joshua Oblong (Demo as Template)
 */
public class GameLoop implements Runnable, EventListener {

    private static final String TAG = GameLoop.class.getSimpleName();
    private static final double SLOW_MOTION_FACTOR = 0.5;

    public double deltaTime;
    private long lastTime;
    public boolean slowMotion;

    private boolean running;
    private boolean pause;

    private final int currentLevel;
    private final int startHealth;
    private final int startEcts;

    private final SurfaceHolder surfaceHolder;
    private final GameSurfaceView gameSurfaceView;

    private final Queue<Event> eventQueue;
    private Scene scene;
    private Hud hud;

    // Bitmaps
    private DynamicBitmap characterBitmap;
    private DynamicBitmap professorBackwardBitmap;
    private DynamicBitmap professorForwardBitmap;
    private DynamicBitmap coffeeBitmap;
    private DynamicBitmap klubnateBitmap;
    private DynamicBitmap clockBitmap;
    private DynamicBitmap mathbookBitmap;
    private DynamicBitmap ectsBitmap;
    private DynamicBitmap heartBitmap;

    public GameLoop(SurfaceHolder surfaceHolder, GameSurfaceView gameSurfaceView, int level, int health, int ects) {
        EventSystem.subscribe(this);
        this.surfaceHolder = surfaceHolder;
        this.gameSurfaceView = gameSurfaceView;

        this.running = false;
        this.pause = false;
        this.eventQueue = new ConcurrentLinkedDeque<>();

        this.currentLevel = level;
        this.startHealth = health;
        this.startEcts = ects;
    }

    public boolean isRunning() {
        return running;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }

    public void setPaused(boolean pause) {
        this.pause = pause;
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
            if (!pause)
                update();
            //Render assets
            if (!pause)
                render();
        }

        shutdown();
    }

    private void start() {
        this.lastTime = System.nanoTime();

        Resources res = this.gameSurfaceView.getContext().getResources();
        characterBitmap = new DynamicBitmap(res, R.drawable.character);
        professorBackwardBitmap = new DynamicBitmap(res, R.drawable.professor);
        professorForwardBitmap = new DynamicBitmap(res, R.drawable.professor_inverse);
        coffeeBitmap = new DynamicBitmap(res, R.drawable.coffee);
        klubnateBitmap = new DynamicBitmap(res, R.drawable.klubnate);
        clockBitmap = new DynamicBitmap(res, R.drawable.clock);
        mathbookBitmap = new DynamicBitmap(res, R.drawable.mathbook);
        ectsBitmap = new DynamicBitmap(res, R.drawable.ects);
        heartBitmap = new DynamicBitmap(res, R.drawable.heart);

        Sprite lifeSprite = new Sprite(heartBitmap, 1, 0);
        Sprite ectsIconSprite = new Sprite(ectsBitmap, 6, 0);

        Sprite slowMotion = new Sprite(clockBitmap, 4, 0);
        Sprite speed = new Sprite(coffeeBitmap, 4, 3);
        Sprite invincibility = new Sprite(klubnateBitmap, 4, 3);
        Sprite damage = new Sprite(mathbookBitmap, 4, 0);

        this.scene = new Scene(gameSurfaceView.getWidth(), gameSurfaceView.getHeight(), currentLevel);
        this.hud = new Hud(lifeSprite, ectsIconSprite, slowMotion, speed, damage, invincibility, gameSurfaceView.getWidth(), gameSurfaceView.getHeight());
        this.initGame(scene, hud, currentLevel, startHealth, startEcts);
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

        if (e.getClass() == ResumeEvent.class)
            return processEvent((ResumeEvent) e);

        if (e.getClass() == VelocityEvent.class)
            return processEvent((VelocityEvent) e);

        if (e.getClass() == HealthEvent.class)
            return processEvent((HealthEvent) e);

        if (e.getClass() == ECTSEvent.class)
            return processEvent((ECTSEvent) e);

        if (e.getClass() == BoosterEvent.class)
            return processEvent((BoosterEvent) e);

        if (e.getClass() == LevelEvent.class)
            return processEvent((LevelEvent) e);

        return false;
    }

    private boolean processEvent(LevelEvent e) {
        Log.e(TAG, "----x processEvent(LevelEvent e)");


        return true;
    }

    private boolean processEvent(TouchEvent e) {
        // TouchEvent!
        return true;
    }

    private boolean processEvent(PauseEvent e) {
        Log.d(TAG, "pause event!");
        pause = true;
        unloadBitmaps();
        return true;
    }

    private boolean processEvent(ResumeEvent e) {
        Log.d(TAG, "resume event!");
        this.lastTime = System.nanoTime();
        loadBitmaps();
        pause = false;
        return true;
    }

    private boolean processEvent(VelocityEvent e) {
        scene.moveCamera(-(int) e.getX(), 0);
        return true;
    }

    private boolean processEvent(HealthEvent e) {
        if (e.add()) {
            hud.addLife();
        } else {
            hud.removeLife();
        }

        return true;
    }

    private boolean processEvent(ECTSEvent e) {
        hud.addEcts(e.getEcts());
        return true;
    }

    private boolean processEvent(BoosterEvent e) {
        switch (e.getType()) {
            case SlowMotion:
                slowMotion = e.isActive();
                break;
            case Speed:
            case Damage:
            case Invincibility:
        }

        hud.booster(e.getType(), e.isActive());
        return true;
    }


    private void update() {
        //Calculate time delta for frame independence
        calculateDeltaTime();
        scene.update(deltaTime);
    }

    private void render() {
        Canvas canvas = null;
        try {
            canvas = surfaceHolder.lockCanvas();
            synchronized (surfaceHolder) {
                if (canvas == null) return;

                gameSurfaceView.draw(canvas);
                scene.draw(canvas);
                hud.draw(canvas);
            }
        } finally {
            if (canvas != null) surfaceHolder.unlockCanvasAndPost(canvas);
        }

    }

    void loadBitmaps() {
        characterBitmap.load();
        professorBackwardBitmap.load();
        professorForwardBitmap.load();
        coffeeBitmap.load();
        klubnateBitmap.load();
        clockBitmap.load();
        mathbookBitmap.load();
        ectsBitmap.load();
        heartBitmap.load();
    }

    void unloadBitmaps() {
        characterBitmap.unload();
        professorBackwardBitmap.unload();
        professorForwardBitmap.unload();
        coffeeBitmap.unload();
        klubnateBitmap.unload();
        clockBitmap.unload();
        mathbookBitmap.unload();
        ectsBitmap.unload();
        heartBitmap.unload();
    }

    private void calculateDeltaTime() {
        long time = System.nanoTime();
        deltaTime = ((time - lastTime) / 1e+9f);
        lastTime = time;

        // Slow Motion
        if (slowMotion)
            deltaTime *= SLOW_MOTION_FACTOR;
    }

    @Override
    public void onEvent(Event event) {
        eventQueue.add(event);
    }

    @Override
    public String toString() {
        return "GameLoop";
    }

    private void initGame(Scene scene, Hud hud, int level, int health, int ects) {
        // Hud
        hud.addEcts(ects);

        for (int i = 0; i < health; i++)
            hud.addLife();

        // Scene
        final int sceneCenter = scene.getWidth() / 2;

        // Character
        CharacterObject character = new CharacterObject(characterBitmap, level, health, ects, sceneCenter, -300);
        scene.add(character);

        // StartPlatform
        GameObject startPlatform = new PlatformObject(sceneCenter - 100, -50, 200);
        scene.add(startPlatform);

        int currentY = 50;
        final float maxOffsetX = scene.getWidth() * 0.6f + 50 * level;
        final float platformBaseWith = level == 0 ? 170 : level == 1 ? 100 : 50;
        final float destroyablePlatformRatio = level == 0 ? 0.1f : level == 1 ? 0.2f : 0.5f;

        // probabilities
        Random random = new Random();
        float pPlatform = 0.3f;

        final float pEctsBase = 0.01f;
        final float pBoosterBase = 0.001f;
        final float pProfessorBase = 0.0001f;

        float pEcts = pEctsBase;
        float pBooster = pBoosterBase;
        float pProfessor = pProfessorBase;

        int lastPlatform = 1;                       // step since last platform was placed
        float lastPlatformCenter = sceneCenter;     // center of the last platform

        while (currentY < 100000) {
            int step = 30 + 5 * level;

            // Platform
            if (lastPlatform > 2 && (lastPlatform > 5 || random.nextFloat() <= pPlatform)) {
                int platformWidth = (int) (platformBaseWith + 200 * random.nextFloat());

                float offsetX = maxOffsetX * random.nextFloat();
                float center = sceneCenter + (random.nextFloat() > 0.5f ? offsetX : -offsetX);

                if (random.nextFloat() < destroyablePlatformRatio) {
                    GameObject platform = new DestroyablePlatformObject(center - platformWidth / 2.0f, -currentY, platformWidth, 2);
                    scene.add(platform);
                } else {
                    GameObject platform = new PlatformObject(center - platformWidth / 2.0f, -currentY, platformWidth);
                    scene.add(platform);
                }

                lastPlatformCenter = center;
                lastPlatform = 0;
            }

            if (lastPlatform == 1) {
                pEcts *= 2;
                pBooster *= 1.5;

                if (random.nextFloat() <= pEcts) {
                    GameObject ectsItem = new EctsItemObject(ectsBitmap, 8, 0, 0);
                    ectsItem.setWorldX(lastPlatformCenter - ectsItem.getWidth() / 2.0);
                    ectsItem.setWorldY(-currentY - ectsItem.getHeight());
                    scene.add(ectsItem);
                    pEcts = pEctsBase;
                } else if (random.nextFloat() <= pBoosterBase + pBooster) {
                    float r = random.nextFloat();

                    GameObject booster;
                    if (r < 0.25) {
                        booster = new BoosterItemObject(coffeeBitmap, Booster.Speed, 0, 0);
                    } else if (r < 0.5) {
                        booster = new BoosterItemObject(mathbookBitmap, Booster.Damage, 0, 0);
                    } else if (r < 0.75) {
                        booster = new BoosterItemObject(clockBitmap, Booster.SlowMotion, 0, 0);
                    } else {
                        booster = new BoosterItemObject(klubnateBitmap, Booster.Invincibility, 0, 0);
                    }

                    booster.setWorldX(lastPlatformCenter - booster.getWidth() / 2.0);
                    booster.setWorldY(-currentY - booster.getHeight());
                    scene.add(booster);
                    pBooster = pBoosterBase;
                }

            }

            if (lastPlatform == 0) {
                pProfessor *= 1.2;

                if (random.nextFloat() <= pProfessor) {

                    int deltaX = 350 + random.nextInt(150);
                    ;
                    int deltaY = 50 + random.nextInt(50);
                    int centerX = random.nextInt(scene.getWidth());
                    ProfessorObject prof = new ProfessorObject(professorForwardBitmap, professorBackwardBitmap, 3, 20, centerX + deltaX / 2, -currentY + deltaY / 2, centerX - deltaX / 2, -currentY - deltaY / 2, 100);

                    scene.add(prof);
                    pProfessor = pProfessorBase;
                    lastPlatform = -1;
                }
            }

            lastPlatform++;
            currentY += step;
        }

    }

}
