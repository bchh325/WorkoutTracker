package com.example.basictracker;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Workouts.class}, exportSchema = false, version = 6)
public abstract class WorkoutsDatabase extends RoomDatabase {
    private static final String DB_NAME = "workouts_db";
    private static WorkoutsDatabase instance;

    public static synchronized WorkoutsDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    WorkoutsDatabase.class, DB_NAME)
                    .fallbackToDestructiveMigration().build();
        }
        return instance;
    }
    public abstract WorkoutsDao workoutsDao();
}
