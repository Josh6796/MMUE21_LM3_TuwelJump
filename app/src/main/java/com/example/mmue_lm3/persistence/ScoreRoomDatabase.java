package com.example.mmue_lm3.persistence;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

/**
 * Abstract class for the entire Room Database for the Highscores.
 * It creates an instance of the database which you can use for certain queries.
 *
 * @author Joshua Oblong
 */
@Database(entities = {Score.class}, version = 1)
public abstract class ScoreRoomDatabase extends RoomDatabase {
    public abstract ScoreDao scoreDao();

    private static ScoreRoomDatabase instance;

    public static synchronized ScoreRoomDatabase getDatabase(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context, ScoreRoomDatabase.class, "score_db").build();
        }
        return instance;
    }
}
