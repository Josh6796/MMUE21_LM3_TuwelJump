package com.example.mmue_lm3.activities;

import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mmue_lm3.R;

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
    }
}