/**
 * Activity for the List of Highscores you reached in the Game
 *
 * @author Joshua Oblong
 */
package com.example.mmue_lm3.activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mmue_lm3.R;

public class HighscoreActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_highscore);
    }
}
