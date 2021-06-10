package com.example.mmue_lm3;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;

/**
 * Activity for the Video you see when the Game starts which has also a Skip Function which
 * leads you to the Game's Menu
 *
 * @author Mathias Schwengerer (Demo as Template)
 */
public class VideoActivity extends AppCompatActivity implements MediaPlayer.OnCompletionListener {

    private VideoView videoView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.activity_video);

        this.videoView = findViewById(R.id.videoView);
        this.videoView.setVideoURI(Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.introvideo));
        this.videoView.setOnCompletionListener(this);
        this.videoView.start();
    }

    public void skipButtonClicked(View view) {
        videoView.stopPlayback();

        Intent intent = new Intent(this, MenuActivity.class);
        startActivity(intent);
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        Intent intent = new Intent(this, MenuActivity.class);
        startActivity(intent);
    }
}
