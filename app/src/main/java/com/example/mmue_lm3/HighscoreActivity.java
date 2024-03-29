package com.example.mmue_lm3;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mmue_lm3.persistence.Score;
import com.example.mmue_lm3.persistence.ScoreRoomDatabase;

import java.util.List;
import java.util.concurrent.Executors;

/**
 * Activity for the List of Highscores you reached in the Game.
 *
 * @author Joshua Oblong (Demo as Template)
 */
public class HighscoreActivity extends AppCompatActivity {

    private interface OnScoresLoadedListener {
        void onScoresLoaded(List<Score> scores);
    }

    private final OnScoresLoadedListener onScoresLoadedListener = this::updateScoresTable;

    private TextView textViewHighscores;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_highscore);

        textViewHighscores = findViewById(R.id.textViewHighscores);

        // Load scores
        Executors.newSingleThreadExecutor().execute(() -> {
            List<Score> scores = loadScores();
            runOnUiThread(() -> onScoresLoadedListener.onScoresLoaded(scores));
        });
    }

    private List<Score> loadScores() {
        return ScoreRoomDatabase.getDatabase(this).scoreDao().selectAllScoresDesc();
    }

    private void updateScoresTable(List<Score> scores) {
        StringBuilder text = new StringBuilder();
        for (Score score : scores) {
            text.append(score.score).append("\n");
        }
        textViewHighscores.setText(text.toString());
    }

    public void backToMenuButtonClicked(View view) {
        Intent intent = new Intent(this, MenuActivity.class);
        startActivity(intent);
    }
}
