package com.example.gymapp.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import com.example.gymapp.exercises.ExerciseDetailsViewModel
import com.example.gymapp.ui.AppTheme
import com.example.gymapp.workout.AddWorkoutExerciseScreen
import com.example.gymapp.workout.WorkoutViewModel

class AddExerciseToWorkoutFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val exerciseDetailsViewModel: ExerciseDetailsViewModel by activityViewModels()
        val workoutViewModel: WorkoutViewModel by activityViewModels()
        return ComposeView(requireContext()).apply {
            setContent {
                AppTheme {
                    AddWorkoutExerciseScreen(
                        exerciseViewModel = exerciseDetailsViewModel,
                        workoutViewModel = workoutViewModel,
                        onNavigateBack = { findNavController().popBackStack() }
                    )
                }
            }
        }
    }
}