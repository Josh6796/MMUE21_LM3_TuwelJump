package com.example.mmue_lm3.persistence;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

/**
 * Interface for the Score Data Access Object which enables us to make certain database queries.
 *
 * @author Joshua Oblong
 */
@Dao
public interface ScoreDao {
    @Insert
    void insert(Score score);
    @Query("SELECT * FROM Score ORDER BY score DESC")
    List<Score> selectAllScoresDesc();
}


