package com.example.gymapp.ui.screens.exercise

import com.example.gymapp.domain.exercises.Exercise
import com.example.gymapp.domain.exercises.ExerciseCategory
import com.example.gymapp.util.UiText

data class ExerciseUiState(
    val selectedExerciseCategory: ExerciseCategory? = null,
    val exerciseCategories: List<ExerciseCategory> = emptyList(),
    val exercises: List<Exercise> = emptyList(),
    val uiEvent: ExerciseUiEvent? = null,
    val isLoading: Boolean = true,
    val selectedExercise: Exercise? = null,
    val uiText: UiText? = null,
    val isError: Boolean = false,
    val selectionExpanded: Boolean = false,
)