

package com.example.mmue_lm3;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.VelocityTracker;

import com.example.mmue_lm3.events.EventSystem;
import com.example.mmue_lm3.events.LevelEvent;
import com.example.mmue_lm3.events.ResumeEvent;
import com.example.mmue_lm3.events.TouchEvent;
import com.example.mmue_lm3.events.VelocityEvent;
import com.example.mmue_lm3.interfaces.Event;
import com.example.mmue_lm3.interfaces.EventListener;

/**
 * Class for the GameSurfaceView. Here we basically just start the game loop in a new thread and
 * listen to certain events.
 *
 * @author Joshua Oblong (Demo as Template)
 */
public class GameSurfaceView extends SurfaceView implements SurfaceHolder.Callback, EventListener {

    private static final String TAG = GameSurfaceView.class.getSimpleName();

    private GameLoop gameLoop;
    private Thread gameMainThread;

    private int currentLevel;
    private int startHealth;
    private int startEcts;

    private VelocityTracker velocityTracker = null;

    public GameSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        getHolder().addCallback(this);
        setFocusable(true);
        EventSystem.subscribe(this);
    }

    private void startGame(SurfaceHolder holder) {
        if (gameLoop == null) {
            gameLoop = new GameLoop(holder, this, currentLevel, startHealth, startEcts);
            gameMainThread = new Thread(gameLoop);
            gameMainThread.start();
        } else {
            EventSystem.onEvent(new ResumeEvent());
        }
    }

    public void endGame() {
        if (!gameLoop.isRunning())
            return;

        gameLoop.setRunning(false);
        try {
            gameMainThread.join();
        } catch (InterruptedException e) {
            Log.e("Error", e.getMessage());
        }
    }

    @Override
    public void onEvent(Event event) {
        if (event.getClass() == LevelEvent.class) {
            LevelEvent e = (LevelEvent) event;
            this.currentLevel = e.getLevel();
            this.startHealth = e.getHealth();
            this.startEcts = e.getEcts();
        }
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

    @Override
    public String toString() {
        return "GameSurfaceView";
    }
}

