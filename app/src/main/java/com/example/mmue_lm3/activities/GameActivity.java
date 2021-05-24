package com.example.mmue_lm3.activities;

import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mmue_lm3.GameLoop;
import com.example.mmue_lm3.R;
import com.example.mmue_lm3.events.EventSystem;
import com.example.mmue_lm3.events.LevelEvent;
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
    private static final String TAG = GameActivity.class.getSimpleName();

    // specifies if the game (GameLoop) is paused
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
        mediaPlayer.setLooping(true);
        mediaPlayer.start();
    }

    @Override
    protected void onResume() {
        super.onResume();

        Intent intent = getIntent();
        int level = intent.getIntExtra("Level", 0);
        int health = intent.getIntExtra("Health", 3);
        int ects = intent.getIntExtra("Ects", 0);

        EventSystem.onEvent(new LevelEvent(level, health, ects));
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventSystem.unsubscribe(this);
        mediaPlayer.stop();
        mediaPlayer.release();
    }

    protected void onGameLost(int score) {
        Intent intent = new Intent(this, LoseActivity.class);
        intent.putExtra("Score", score);
        startActivity(intent);
    }

    protected void onGameWon(int level, int health, int ects) {
        Intent intent = new Intent(this, WinActivity.class);
        intent.putExtra("Level", level);
        intent.putExtra("Health", health);
        intent.putExtra("Ects", ects);
        startActivity(intent);
    }

    public void pauseButtonClicked(View view) {
        ImageView helpdialog = (ImageView) findViewById(R.id.helpDialog);
        if (isPaused) {
            EventSystem.onEvent(new ResumeEvent());
            isPaused = false;
            helpdialog.setVisibility(View.INVISIBLE);
        } else {
            isPaused = true;
            EventSystem.onEvent(new PauseEvent());
            helpdialog.setVisibility(View.VISIBLE);
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
            onGameLost(((LoseEvent) event).getScore());
        if (event.getClass() == WinEvent.class)
        {
            WinEvent e = (WinEvent)event;
            onGameWon(e.getLevel(), e.getHealth(), e.getEcts());
        }
    }
}
