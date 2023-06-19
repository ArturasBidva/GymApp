package com.example.gymapp.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.navArgs
import com.example.gymapp.workout.WorkoutDetailScreen
import com.example.gymapp.workout.WorkoutDetailsFragmentArgs
import com.example.gymapp.workout.WorkoutViewModel

class WorkoutDetailsFragment : Fragment(
) {
    private val exerciseDetailsViewModel: WorkoutViewModel by activityViewModels()
    private val args: WorkoutDetailsFragmentArgs by navArgs()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        exerciseDetailsViewModel.getWorkoutById(args.workoutId)
        return ComposeView(requireContext()).apply {
            setContent {
                MaterialTheme {
                WorkoutDetailScreen(viewModel = exerciseDetailsViewModel)
                }
            }
        }
    }
}