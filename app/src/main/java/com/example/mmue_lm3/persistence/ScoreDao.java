package com.example.mmue_lm3.persistence;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface ScoreDao {
    @Insert
    void insert(Score score);
    @Query("SELECT * FROM Score ORDER BY score DESC")
    List<Score> selectAllScoresDesc();
    @Query("SELECT * FROM Score WHERE id = :id")
    Score selectById(int id);
}


