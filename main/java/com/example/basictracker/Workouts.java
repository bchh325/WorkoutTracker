package com.example.basictracker;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "workoutsTable")
public class Workouts {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    protected int id;


    @ColumnInfo(name = "workout_name")
    private String workoutName;

    @ColumnInfo(name = "num_sets")
    private int sets;

    @ColumnInfo(name = "num_reps")
    private int reps;

    @ColumnInfo(name = "num_weight")
    private int weight;

    @ColumnInfo(name = "date")
    private String date;

    public Workouts(String workoutName, int sets, int reps, int weight, String date) {
        this.workoutName = workoutName;
        this.sets = sets;
        this.reps = reps;
        this.weight = weight;
        this.date = date;
    }

    public String getWorkoutName() { return this.workoutName; }

    public int getId() { return this.id; }

    public int getSets() {
        return this.sets;
    }

    public int getReps() { return this.reps; }

    public int getWeight() { return this.weight; }

    public String getDate() { return this.date; }

}
