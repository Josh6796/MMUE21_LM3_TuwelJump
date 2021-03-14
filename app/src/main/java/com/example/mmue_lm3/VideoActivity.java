/**
 * Activity for the Video you see when the Game starts
 *
 * @author Mathias Schwengerer
 */
package com.example.mmue_lm3;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class VideoActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);
    }

    public void skipButtonClicked(View view) {
        Intent intent = new Intent(this, MenuActivity.class);
        startActivity(intent);
    }

    // TODO: Go to Menu after Video ended
    public void onVideoEnded() {
    }
}
