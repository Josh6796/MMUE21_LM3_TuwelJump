package com.example.mmue_lm3;


import android.content.res.Resources;
import android.graphics.Canvas;
import android.util.Log;
import android.view.SurfaceHolder;

import com.example.mmue_lm3.enums.Booster;
import com.example.mmue_lm3.events.ECTSEvent;
import com.example.mmue_lm3.events.EventSystem;
import com.example.mmue_lm3.events.HealthEvent;
import com.example.mmue_lm3.events.PauseEvent;
import com.example.mmue_lm3.events.ResumeEvent;
import com.example.mmue_lm3.events.VelocityEvent;
import com.example.mmue_lm3.gameobjects.BoosterItemObject;
import com.example.mmue_lm3.gameobjects.DestroyablePlatformObject;
import com.example.mmue_lm3.gameobjects.PlatformObject;
import com.example.mmue_lm3.gameobjects.ProfessorObject;
import com.example.mmue_lm3.sprites.EventAnimatedSprite;
import com.example.mmue_lm3.sprites.Sprite;
import com.example.mmue_lm3.interfaces.Event;
import com.example.mmue_lm3.interfaces.EventListener;
import com.example.mmue_lm3.events.TouchEvent;
import com.example.mmue_lm3.gameobjects.CharacterObject;
import com.example.mmue_lm3.gameobjects.EctsItemObject;
import com.example.mmue_lm3.gameobjects.GameObject;
import com.example.mmue_lm3.sprites.TimeAnimatedSprite;

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
    private boolean pause;

    private final SurfaceHolder surfaceHolder;
    private final GameSurfaceView gameSurfaceView;

    private final Queue<Event> eventQueue;
    private Scene gameScene;
    private Hud hud;

    // Sprites
    private EventAnimatedSprite characterSprite;

    private TimeAnimatedSprite professorSprite;
    private TimeAnimatedSprite coffeeSprite;
    private TimeAnimatedSprite klubnateSprite;
    private TimeAnimatedSprite clockSprite;
    private TimeAnimatedSprite mathbookSprite;
    private TimeAnimatedSprite ectsSprite;

    private Sprite lifeSprite;
    private Sprite ectsIconSprite;


    public GameLoop(SurfaceHolder surfaceHolder, GameSurfaceView gameSurfaceView) {
        EventSystem.subscribe(this);
        this.surfaceHolder = surfaceHolder;
        this.gameSurfaceView = gameSurfaceView;

        this.pause = false;


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
        characterSprite = new EventAnimatedSprite(res, R.drawable.character, 2, 0, 1, false);

        professorSprite = new TimeAnimatedSprite(res, R.drawable.professor, 8, .15, 0, 7, true);
        coffeeSprite = new TimeAnimatedSprite(res, R.drawable.coffee, 4, .15, 0, 3, true);
        klubnateSprite = new TimeAnimatedSprite(res, R.drawable.klubnate, 4, .15, 0, 3, true);
        clockSprite = new TimeAnimatedSprite(res, R.drawable.clock, 4, .15, 0, 3, true);
        mathbookSprite = new TimeAnimatedSprite(res, R.drawable.mathbook, 4, .15, 0, 3, true);
        ectsSprite = new TimeAnimatedSprite(res, R.drawable.ects, 6, .15, 0, 5, true);

        lifeSprite = new Sprite(res, R.drawable.heart, 1, 0);
        ectsIconSprite = new Sprite(res, R.drawable.ects, 6, 0);

        this.gameScene = new Scene(gameSurfaceView.getWidth(), gameSurfaceView.getHeight());
        this.hud = new Hud(lifeSprite, ectsIconSprite, gameSurfaceView.getWidth(), gameSurfaceView.getHeight());
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

        if (e.getClass() == ResumeEvent.class)
            return processEvent((ResumeEvent) e);

        if (e.getClass() == VelocityEvent.class)
            return processEvent((VelocityEvent) e);

        if (e.getClass() == HealthEvent.class)
            return processEvent((HealthEvent) e);

        if (e.getClass() == ECTSEvent.class)
            return processEvent((ECTSEvent) e);


        return false;
    }

    private boolean processEvent(TouchEvent e) {
        // TouchEvent!
        return true;
    }

    private boolean processEvent(PauseEvent e) {
        Log.d(TAG, "pause event!");
        pause = true;
        recycleSprites();
        return true;
    }

    private boolean processEvent(ResumeEvent e) {
        Log.d(TAG, "resume event!");
        this.lastTime = System.nanoTime();
        loadSprites();
        pause = false;
        return true;
    }

    private boolean processEvent(VelocityEvent e) {
        gameScene.moveCamera(-(int) e.getX(), 0);
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
                hud.draw(canvas);
            }
        } finally {
            if (canvas != null) surfaceHolder.unlockCanvasAndPost(canvas);
        }

    }

    void recycleSprites() {
        characterSprite.recycle();
        professorSprite.recycle();
        coffeeSprite.recycle();
        klubnateSprite.recycle();
        clockSprite.recycle();
        mathbookSprite.recycle();
        ectsSprite.recycle();
        lifeSprite.recycle();
        ectsIconSprite.recycle();
    }

    void loadSprites() {
        characterSprite.load();
        professorSprite.load();
        coffeeSprite.load();
        klubnateSprite.load();
        clockSprite.load();
        mathbookSprite.load();
        ectsSprite.load();
        lifeSprite.load();
        ectsIconSprite.load();
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

    @Override
    public String toString() {
        return "GameLoop";
    }

    // TODO: remove (just for testing)
    void initScene(Scene scene) {


        // Character
        CharacterObject character = new CharacterObject(characterSprite, 3, 0, 500, 1300);
        scene.add(character);

        hud.addLife();
        hud.addLife();
        hud.addLife();

        // Booster
        GameObject booster_1 = new BoosterItemObject(klubnateSprite, Booster.Speed, 300, 1300);
        GameObject booster_2 = new BoosterItemObject(coffeeSprite, Booster.Damage, 600, 1300);
        GameObject booster_3 = new BoosterItemObject(clockSprite, Booster.SlowMotion, 200, 1500);
        GameObject booster_4 = new BoosterItemObject(mathbookSprite, Booster.Invincibility, 700, 1500);
        scene.add(booster_1);
        scene.add(booster_2);
        scene.add(booster_3);
        scene.add(booster_4);

        // Items
        GameObject ects_1 = new EctsItemObject(ectsSprite, 20, 50, 1200);
        GameObject ects_2 = new EctsItemObject(ectsSprite, 20, 50, 600);
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
        GameObject platform_8 = new DestroyablePlatformObject(550, 1120, 200, 2);
        GameObject platform_9 = new DestroyablePlatformObject(150, 1050, 250, 2);
        GameObject platform_10 = new DestroyablePlatformObject(700, 590, 240, 1);
        scene.add(platform_1);
        scene.add(platform_2);
        scene.add(platform_3);
        scene.add(platform_4);
        scene.add(platform_5);
        scene.add(platform_6);
        scene.add(platform_7);
        scene.add(platform_8);
        scene.add(platform_9);
        scene.add(platform_10);

        // Professor
        ProfessorObject prof_1 = new ProfessorObject(professorSprite, 5, 6, 920, 720);
        ProfessorObject prof_2 = new ProfessorObject(professorSprite, 5, 6, 700, 20);
        scene.add(prof_1);
        scene.add(prof_2);
    }

}
