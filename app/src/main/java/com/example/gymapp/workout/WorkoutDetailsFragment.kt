package com.example.gymapp.workout

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.navArgs

class WorkoutDetailsFragment : Fragment(
) {
    private val exerciseDetailsViewModel: WorkoutViewModel by activityViewModels()
    private val args: WorkoutDetailsFragmentArgs by navArgs()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return ComposeView(requireContext()).apply {
            exerciseDetailsViewModel.getWorkoutById(args.workoutId)
            setContent {
                MaterialTheme {
                WorkoutDetailScreen(viewModel = exerciseDetailsViewModel )
                }
            }
        }
    }
}