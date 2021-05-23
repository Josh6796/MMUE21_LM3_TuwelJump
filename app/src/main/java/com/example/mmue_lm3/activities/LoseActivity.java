package com.example.mmue_lm3.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mmue_lm3.R;
import com.example.mmue_lm3.persistence.Score;
import com.example.mmue_lm3.persistence.ScoreRoomDatabase;
import com.example.mmue_lm3.util.Concurrency;

/**
 * Activity for the Splash Screen that comes when you lost the game with the View Animation
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
        Concurrency.executeAsync(() -> saveScore(new Score(score)));

        TextView textViewScore = findViewById(R.id.textViewScore);
        textViewScore.setText("ECTS: " + score);
    }

    public void saveScore(Score score) {
        ScoreRoomDatabase.getInstance(this).scoreDao().insert(score);
    }
}
