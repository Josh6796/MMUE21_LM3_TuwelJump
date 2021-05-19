package com.example.mmue_lm3.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mmue_lm3.R;
import com.example.mmue_lm3.persistence.Score;
import com.example.mmue_lm3.persistence.ScoreRoomDatabase;
import com.example.mmue_lm3.util.Concurrency;

/**
 * Activity for the Game's Menu
 *
 * @author Joshua Oblong (Demo as Template)
 */
public class MenuActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_menu);



        Concurrency.executeAsync(() -> saveScore(new Score(500)));
        Concurrency.executeAsync(() -> saveScore(new Score(900)));
        Concurrency.executeAsync(() -> saveScore(new Score(700)));
        Concurrency.executeAsync(() -> saveScore(new Score(400)));
    }

    /**
     * This Method handles clicking on buttonStartGame
     *
     * @param view view of buttonStartGame
     */
    public void startButtonClicked(View view) {
        Intent intent = new Intent(this, GameActivity.class);
        startActivity(intent);
    }

    /**
     * This Method handles clicking on buttonSeeHighscore
     *
     * @param view view of buttonSeeHighscores
     */
    public void highscoreButtonClicked(View view) {
        Intent intent = new Intent(this, HighscoreActivity.class);
        startActivity(intent);
    }

    public void saveScore(Score score) {
        ScoreRoomDatabase.getInstance(this).userDao().insert(score);
    }
}
