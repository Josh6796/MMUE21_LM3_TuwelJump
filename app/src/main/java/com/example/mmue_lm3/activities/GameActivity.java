package com.example.mmue_lm3.activities;

import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

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
    private MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventSystem.subscribe(this);
        setContentView(R.layout.activity_game);

        // Sounds
        AudioManager audioManager = (AudioManager) getSystemService(GameActivity.AUDIO_SERVICE);
        audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, audioManager.getStreamVolume(AudioManager.STREAM_MUSIC), 0);
        mediaPlayer = MediaPlayer.create(this, R.raw.background);
        mediaPlayer.start();
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
        mediaPlayer.stop();
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
        ImageView helpdialog = (ImageView) findViewById(R.id.helpDialog);
        if (isPaused) {
            EventSystem.onEvent(new ResumeEvent());
            isPaused = false;
            helpdialog.setVisibility(View.INVISIBLE);
            //this.onResume();
        } else {
            isPaused = true;
            EventSystem.onEvent(new PauseEvent());
            helpdialog.setVisibility(View.VISIBLE);
            //this.onPause();
        }
    }

    public void muteButtonClicked(View view) {
        if (mediaPlayer.isPlaying())
            mediaPlayer.pause();
        else
            mediaPlayer.start();
    }

    @Override
    public void onEvent(Event event) {
        if (event.getClass() == LoseEvent.class)
            onGameLost();
        if (event.getClass() == WinEvent.class)
            onGameWon();
    }
}
