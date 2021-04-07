package com.example.mmue_lm3;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.VelocityTracker;
import android.widget.Toast;

import com.example.mmue_lm3.events.EventSystem;
import com.example.mmue_lm3.events.TouchEvent;
import com.example.mmue_lm3.events.VelocityEvent;
import com.example.mmue_lm3.gameobjects.GameObject;

public class GameSurfaceView extends SurfaceView implements SurfaceHolder.Callback {

    private static final String TAG = GameSurfaceView.class.getSimpleName();

    private GameLoop gameLoop;
    private Thread gameMainThread;
    private GameObject character;

    private VelocityTracker velocityTracker = null;

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

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent e) {
        int index = e.getActionIndex();
        int action = e.getActionMasked();
        int pointerId = e.getPointerId(index);

        switch (action) {
            case MotionEvent.ACTION_DOWN:
                EventSystem.onEvent(new TouchEvent((int) e.getX(), (int) e.getY()));

                if (velocityTracker == null)
                    velocityTracker = VelocityTracker.obtain();
                else
                    velocityTracker.clear();

                velocityTracker.addMovement(e);
                break;

            case MotionEvent.ACTION_MOVE:
                velocityTracker.addMovement(e);
                velocityTracker.computeCurrentVelocity(10);
                EventSystem.onEvent(new VelocityEvent(velocityTracker.getXVelocity(pointerId), velocityTracker.getYVelocity(pointerId)));

                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                //velocityTracker.recycle();
                velocityTracker = null;
                break;
        }

        return true;
    }
}

