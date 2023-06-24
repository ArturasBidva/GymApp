package com.example.gymapp.domain.workouts

import android.os.Parcelable
import com.example.gymapp.domain.workouts.ExerciseWorkouts
import kotlinx.parcelize.Parcelize

@Parcelize
data class Workout(
    val id: Long,
    val title: String,
    val description: String,
    val exerciseWorkouts : List<ExerciseWorkouts>
) : Parcelable {
    constructor(title: String, description: String) : this(
        id = 0L,
        title = "",
        description = "",
        exerciseWorkouts = listOf()
    )
}
