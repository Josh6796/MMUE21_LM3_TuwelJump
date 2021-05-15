package com.example.mmue_lm3.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mmue_lm3.R;
import com.example.mmue_lm3.events.EventSystem;
import com.example.mmue_lm3.events.LoseEvent;
import com.example.mmue_lm3.events.PauseEvent;
import com.example.mmue_lm3.events.ResumeEvent;
import com.example.mmue_lm3.events.WinEvent;
import com.example.mmue_lm3.interfaces.Event;
import com.example.mmue_lm3.interfaces.EventListener;

/**
 * Activity for the Game itself
 *
 * @author Joshua Oblong (Demo as Template)
 */
public class GameActivity extends AppCompatActivity implements EventListener {

    private boolean isPaused = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventSystem.subscribe(this);
        setContentView(R.layout.activity_game);
    }

    @Override
    protected void onPause() {
        super.onPause();
        isPaused = true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        isPaused = false;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventSystem.unsubscribe(this);
    }

    protected void onGameLost() {
        Intent intent = new Intent(this, LoseActivity.class);
        startActivity(intent);
    }

    protected void onGameWon() {
        Intent intent = new Intent(this, WinActivity.class);
        startActivity(intent);
    }

    public void pauseButtonClicked(View view) {
        if (isPaused) {
            EventSystem.onEvent(new ResumeEvent());
            isPaused = false;
            //this.onResume();
        } else {
            isPaused = true;
            EventSystem.onEvent(new PauseEvent());
            //this.onPause();
        }
    }

    public void muteButtonClicked(View view) {
        // TODO: Mute Button Action
    }

    @Override
    public void onEvent(Event event) {
        if (event.getClass() == LoseEvent.class)
            onGameLost();
        if (event.getClass() == WinEvent.class)
            onGameWon();
    }
}
