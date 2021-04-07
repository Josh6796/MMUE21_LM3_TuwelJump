package com.example.mmue_lm3;


import android.annotation.SuppressLint;
import android.graphics.Canvas;
import android.util.Log;
import android.view.SurfaceHolder;

import com.example.mmue_lm3.GameSurfaceView;
import com.example.mmue_lm3.Scene;
import com.example.mmue_lm3.gameobjects.CharacterObject;
import com.example.mmue_lm3.gameobjects.EctsItemObject;
import com.example.mmue_lm3.gameobjects.GameObject;

public class GameLoop implements Runnable {

    public float deltaTime;
    private long lastTime;

    private SurfaceHolder surfaceHolder;
    private GameSurfaceView gameSurfaceView;
    private Scene gameScene;
    private boolean running;

    public GameLoop(SurfaceHolder surfaceHolder, GameSurfaceView gameSurfaceView) {
        this.surfaceHolder = surfaceHolder;
        this.gameSurfaceView = gameSurfaceView;

        this.gameScene = new Scene();
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

        while (running){
            //Update game logic
            update();
            //Render assets
            render();
        }
    }

    private void start() {
        this.lastTime = System.nanoTime();
        GameObject test1 = new EctsItemObject(20, 50, 500);
        GameObject test2 = new CharacterObject(20, 29, 100, 500);
        GameObject test3 = new CharacterObject(20, 29, 200, 500);
        GameObject test4 = new EctsItemObject(20, 50, 550);
        gameScene.add(test1);
        gameScene.add(test2);
        gameScene.add(test3);
        gameScene.add(test4);
    }

    private void update() {
        //Calculate time delta for frame independence
        calculateDeltaTime();
        gameScene.update(deltaTime);
    }

    //@SuppressLint("WrongCall")
    private void render() {
        Canvas canvas = null;
        try{
            canvas = surfaceHolder.lockCanvas();
            synchronized (surfaceHolder){
                if(canvas == null) return;

                gameSurfaceView.draw(canvas);
                gameScene.draw(canvas);
            }
        } finally {
            if(canvas != null) surfaceHolder.unlockCanvasAndPost(canvas);
        }

    }

    private void calculateDeltaTime() {
        long time = System.nanoTime();
        this.deltaTime = ((time - this.lastTime) / 1000000.0f);
        lastTime = time;
    }
}
