package com.example.mmue_lm3;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class GameSurfaceView extends SurfaceView implements SurfaceHolder.Callback {


    private GameLoop gameLoop;
    private Thread gameMainThread;

    public GameSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        getHolder().addCallback(this);
        setFocusable(true);

        loadAssets();
    }

    private void startGame(SurfaceHolder holder) {
        gameLoop = new GameLoop(holder, this);
        gameMainThread = new Thread(gameLoop);
        gameMainThread.start();
    }

    private void endGame() {
        gameLoop.setRunning(false);
        try {
            gameMainThread.join();
        } catch (InterruptedException e) {
            Log.e("Error", e.getMessage());
        }
    }

    private void loadAssets() {
        // Initialize the assets:
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        startGame(holder);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        endGame();
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
    }
}

