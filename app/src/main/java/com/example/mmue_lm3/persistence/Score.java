package com.example.mmue_lm3.persistence;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * Class for the Score Database-Entity which generates the table for Highscores
 *
 * @author Joshua Oblong
 */
@Entity(tableName = "score")
public class Score {
    @PrimaryKey(autoGenerate = true)
    public int id;

    public int score;

    public Score(int score) {
        this.score = score;
    }
}
