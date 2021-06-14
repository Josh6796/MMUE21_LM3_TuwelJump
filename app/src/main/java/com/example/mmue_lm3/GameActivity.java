package com.example.mmue_lm3;

import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mmue_lm3.events.EventSystem;
import com.example.mmue_lm3.events.LevelEvent;
import com.example.mmue_lm3.events.LoseEvent;
import com.example.mmue_lm3.events.PauseEvent;
import com.example.mmue_lm3.events.ResumeEvent;
import com.example.mmue_lm3.events.WinEvent;
import com.example.mmue_lm3.interfaces.Event;
import com.example.mmue_lm3.interfaces.EventListener;

/**
 * Activity for the Game itself where we listen to Pause and Resume Events to pause or resume
 * the game and Lose or Win Events to get to the next level or show the Losing Splashscreen.
 * There's also a Mediaplayer for the background music which has a mute functionality.
 * Also it's showing a Help Dialog when pressing the Pause Button
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
    }

    @Override
    protected void onStart() {
        super.onStart();

        mediaPlayer.start();
    }

    @Override
    protected void onResume() {
        super.onResume();

        ImageView helpdialog = (ImageView) findViewById(R.id.helpDialog);

        EventSystem.onEvent(new ResumeEvent());
        isPaused = false;
        helpdialog.setVisibility(View.INVISIBLE);

        Intent intent = getIntent();
        int level = intent.getIntExtra("Level", 0);
        int health = intent.getIntExtra("Health", 3);
        int ects = intent.getIntExtra("Ects", 0);

        EventSystem.onEvent(new LevelEvent(level, health, ects));
    }

    @Override
    protected void onPause() {
        super.onPause();

        ImageView helpdialog = (ImageView) findViewById(R.id.helpDialog);

        isPaused = true;
        EventSystem.onEvent(new PauseEvent());
        helpdialog.setVisibility(View.VISIBLE);
        helpdialog.bringToFront();
    }

    @Override
    protected void onStop() {
        super.onStop();

        mediaPlayer.pause();
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
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        startActivity(intent);
        this.finish();
    }

    protected void onGameWon(int level, int health, int ects) {
        Intent intent = new Intent(this, WinActivity.class);
        intent.putExtra("Level", level);
        intent.putExtra("Health", health);
        intent.putExtra("Ects", ects);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        startActivity(intent);
        this.finish();
    }

    public void pauseButtonClicked(View view) {
        if (isPaused) {
            onResume();
        } else {
            onPause();
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

    @Override
    public void onBackPressed() {
        if (isPaused) {
            onResume();
        } else {
            onPause();
        }
    }
}
