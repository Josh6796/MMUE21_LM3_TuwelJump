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

/**
 * Activity for the Splash Screen that comes when you won the game with the View Animation and
 * the achieved Highscore. It also handles if you go to the next Level.
 *
 * @author Joshua Oblong (Demo as Template)
 */
public class WinActivity extends AppCompatActivity {

    private int level;
    private int health;
    private int ects;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_win);

        Animation viewAnimation = AnimationUtils.loadAnimation(this, R.anim.winlose_animation);
        TextView text = (TextView) findViewById(R.id.youWonText);
        text.startAnimation(viewAnimation);

        Intent intent = getIntent();
        level = intent.getIntExtra("Level", 0);
        health = intent.getIntExtra("Health", 0);
        ects = intent.getIntExtra("Ects", 0);

        TextView textViewScore = findViewById(R.id.textViewScoreWon);
        textViewScore.setText("ECTS: " + ects);
    }

    public void saveScore(Score score) {
        ScoreRoomDatabase.getDatabase(this).scoreDao().insert(score);
    }

    public void continueButtonClicked(View view) {
        Intent intent = new Intent(this, GameActivity.class);
        intent.putExtra("Level", level + 1);
        intent.putExtra("Health", health);
        intent.putExtra("Ects", ects);
        startActivity(intent);
    }
}
