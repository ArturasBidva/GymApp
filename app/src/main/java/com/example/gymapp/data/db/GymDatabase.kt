package com.example.gymapp.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.gymapp.data.db.entities.ExerciseAndExerciseCategoryCrossRef
import com.example.gymapp.data.db.entities.ExerciseCategoryEntity
import com.example.gymapp.data.db.entities.ExerciseEntity
import com.example.gymapp.data.db.entities.ExerciseWorkoutEntity
import com.example.gymapp.data.db.entities.WorkoutAndExerciseWorkoutCrossRef
import com.example.gymapp.data.db.entities.WorkoutEntity
import com.example.gymapp.data.db.entities.WorkoutExerciseCrossRef
import com.example.gymapp.data.repositories.Converters
import com.example.gymapp.data.repositories.exercise.ExerciseDao
import com.example.gymapp.data.repositories.workout.WorkoutDao

@Database(
    entities = [
        ExerciseEntity::class,
        WorkoutEntity::class,
        ExerciseWorkoutEntity::class,
        WorkoutAndExerciseWorkoutCrossRef::class,
        ExerciseCategoryEntity::class,
        ExerciseAndExerciseCategoryCrossRef::class,
        WorkoutExerciseCrossRef::class,

    ],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class GymDatabase : RoomDatabase() {
    abstract fun exerciseDao(): ExerciseDao
    abstract fun workoutDao(): WorkoutDao

}