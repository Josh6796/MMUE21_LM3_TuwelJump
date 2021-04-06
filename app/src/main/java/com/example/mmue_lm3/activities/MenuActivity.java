/**
 * Activity for the Game's Menu
 *
 * @author Joshua Oblong
 */
package com.example.mmue_lm3.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mmue_lm3.R;

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
