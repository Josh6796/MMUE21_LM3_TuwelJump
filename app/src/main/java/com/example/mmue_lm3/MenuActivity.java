/**
 * Activity for the Game's Menu
 *
 * @author Joshua Oblong
 */
package com.example.mmue_lm3;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class MenuActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_menu);
    }

    /**
     * This Method handles clicking on startGameButton
     *
     * @param view view of startGameButton
     */
    public void startButtonClicked(View view) {
        Intent intent = new Intent(this, GameActivity.class);
        startActivity(intent);
    }
}
