package com.example.basictracker;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.RawQuery;

import java.util.ArrayList;
import java.util.List;

@Dao
public interface WorkoutsDao {

    @Query("SELECT * FROM workoutsTable")
    List<Workouts> getWorkoutsList();

    @Query("DELETE FROM workoutsTable")
    void deleteWorkoutsList();

    @Query("SELECT num_sets FROM workoutsTable WHERE workout_name = :workoutTitle")
    List<Integer> getNumSets(String workoutTitle);

    @Query("SELECT num_reps FROM workoutsTable WHERE workout_name = :workoutTitle")
    List<Integer> getNumReps(String workoutTitle);

    @Query("SELECT num_weight FROM workoutsTable WHERE workout_name = :workoutTitle")
    List<Integer> getNumWeight(String workoutTitle);

    @Query("SELECT date FROM workoutsTable WHERE workout_name = :workoutTitle")
    List<String> getDates(String workoutTitle);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertWorkout(Workouts workouts);
}
