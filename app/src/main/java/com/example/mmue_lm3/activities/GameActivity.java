package com.example.mmue_lm3.activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mmue_lm3.R;

/**
 * Activity for the Game itself
 *
 * @author Joshua Oblong
 */
public class GameActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}
