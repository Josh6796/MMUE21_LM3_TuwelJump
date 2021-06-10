package com.example.mmue_lm3;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mmue_lm3.persistence.Score;
import com.example.mmue_lm3.persistence.ScoreRoomDatabase;

import java.util.concurrent.Executors;

/**
 * Activity for the Splash Screen that comes when you lost the game with the View Animation and
 * the achieved Highscore.
 *
 * @author Joshua Oblong (Demo as Template)
 */
public class LoseActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_lose);

        Animation viewAnimation = AnimationUtils.loadAnimation(this, R.anim.winlose_animation);
        TextView youLostText = (TextView) findViewById(R.id.youLostText);
        youLostText.startAnimation(viewAnimation);

        Intent intent = getIntent();
        int score = intent.getIntExtra("Score", 0);
        Executors.newSingleThreadExecutor().execute(() -> saveScore(new Score(score)));

        TextView textViewScore = findViewById(R.id.textViewScoreLost);
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
