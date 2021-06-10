package com.example.mmue_lm3;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

/**
 * Activity for the Game's Menu where you can start the Game or see your Highscores.
 *
 * @author Joshua Oblong (Demo as Template)
 */
public class MenuActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_menu);
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
}
