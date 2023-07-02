package com.example.gymapp.domain.exercises

sealed interface ExerciseEvent {
    object SaveExercise : ExerciseEvent
    data class SetTitle(val title: String) : ExerciseEvent
    data class SetImgUrl(val imgUrl: String) : ExerciseEvent
    data class SetDescription(val description: String) : ExerciseEvent
    data class SetCategory(val category: List<ExerciseCategory>) : ExerciseEvent
    data class DeleteExercise(val exercise: Exercise) : ExerciseEvent
}