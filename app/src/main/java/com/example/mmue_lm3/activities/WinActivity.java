package com.example.mmue_lm3.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mmue_lm3.R;
import com.example.mmue_lm3.persistence.Score;
import com.example.mmue_lm3.persistence.ScoreRoomDatabase;

import java.util.concurrent.Executors;

/**
 * Activity for the Splash Screen that comes when you won the game with the View Animation
 *
 * @author Joshua Oblong (Demo as Template)
 */
public class WinActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_win);

        Animation viewAnimation = AnimationUtils.loadAnimation(this, R.anim.winlose_animation);
        TextView text = (TextView) findViewById(R.id.youWonText);
        text.startAnimation(viewAnimation);

        Intent intent = getIntent();
        int score = intent.getIntExtra("Score", 0);
        Executors.newSingleThreadExecutor().execute(() -> saveScore(new Score(score)));

        TextView textViewScore = findViewById(R.id.textViewScoreWon);
        textViewScore.setText("ECTS: " + score);
    }

    public void saveScore(Score score) {
        ScoreRoomDatabase.getDatabase(this).scoreDao().insert(score);
    }

    public void highscoreButtonClicked(View view) {
        Intent intent = new Intent(this, HighscoreActivity.class);
        startActivity(intent);
    }
}
