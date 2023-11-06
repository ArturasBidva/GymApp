package com.example.gymapp.ui.screens.workout

import com.example.gymapp.domain.workouts.ExerciseWorkout
import com.example.gymapp.domain.workouts.Workout

sealed class WorkoutUiEvent {
    class DeleteWorkout(val workout: Workout): WorkoutUiEvent()
    class SaveWorkout(val workout: Workout): WorkoutUiEvent()
    object SaveWorkoutSuccess : WorkoutUiEvent()
    object DeleteWorkoutSuccess : WorkoutUiEvent()
    class SelectWorkout(val workout: Workout): WorkoutUiEvent()
    object DismissWorkoutDialog: WorkoutUiEvent()
    object DismissAddExerciseToWorkoutDialog: WorkoutUiEvent()
    class CreateExerciseWorkout(val exerciseWorkout: ExerciseWorkout, val workout : Workout) : WorkoutUiEvent()
    class DeleteExerciseWorkoutFromWorkout(val workoutId: Long, val exerciseId: Long): WorkoutUiEvent()
}