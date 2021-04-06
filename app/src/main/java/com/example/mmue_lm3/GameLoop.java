package com.example.mmue_lm3;


import android.annotation.SuppressLint;
import android.graphics.Canvas;
import android.util.Log;
import android.view.SurfaceHolder;

public class GameLoop implements Runnable {

    public float deltaTime;

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
        //todo: calculate the time delta between the last frame and the current frame
    }

    private void update() {
        //todo: calculate the time delta between the last frame and the current frame
        //Calculate time delta for frame independence
        calculateDeltaTime();
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
        //todo: calculate the time delta between the last frame and the current frame
    }

}
