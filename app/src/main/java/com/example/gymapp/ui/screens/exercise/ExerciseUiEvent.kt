package com.example.gymapp.ui.screens.exercise

import com.example.gymapp.domain.exercises.Exercise
import com.example.gymapp.domain.exercises.ExerciseCategory

sealed class ExerciseUiEvent {
    class SelectExerciseCategory(val exerciseCategory: ExerciseCategory) : ExerciseUiEvent()
    class SelectExercise(val exercise: Exercise): ExerciseUiEvent()
    class DeleteExercise(val exercise: Exercise): ExerciseUiEvent()
    object DeleteExerciseSuccess : ExerciseUiEvent()
    object CloseAddExerciseToWorkoutDialog : ExerciseUiEvent()
}
