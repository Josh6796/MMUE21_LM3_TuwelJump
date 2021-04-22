package com.example.mmue_lm3.activities;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mmue_lm3.R;
import com.example.mmue_lm3.events.EventSystem;
import com.example.mmue_lm3.events.PauseEvent;
import com.example.mmue_lm3.events.ResumeEvent;

/**
 * Activity for the Game itself
 *
 * @author Joshua Oblong (Demo as Template)
 */
public class GameActivity extends AppCompatActivity {

    private boolean isPaused = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
}
