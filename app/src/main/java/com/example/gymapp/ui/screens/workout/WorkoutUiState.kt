package com.example.gymapp.ui.screens.workout

import com.example.gymapp.domain.workouts.ExerciseWorkout
import com.example.gymapp.domain.workouts.Workout
import com.example.gymapp.util.UiText

data class WorkoutUiState(
    val isLoading: Boolean = true,
    val workouts: List<Workout> = listOf(),
    var workoutInfo: Workout? = null,
    val uiEvent: WorkoutUiEvent? = null,
    val uiText: UiText? = null,
    val workout: Workout? = null,
    val isCreated: Boolean = false,
    val exerciseWorkouts: List<ExerciseWorkout> = listOf(),
    val exerciseWorkout: ExerciseWorkout? = null,
    val selectedWorkout: Workout? = null,
    var isDialogOpen: Boolean = true
)