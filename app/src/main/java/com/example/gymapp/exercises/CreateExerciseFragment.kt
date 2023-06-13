package com.example.gymapp.exercises

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import com.example.gymapp.ui.AppTheme
import com.example.gymapp.workout.WorkoutViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CreateExerciseFragment : Fragment() {
    private val exerciseDetailsViewModel: ExerciseDetailsViewModel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return ComposeView(requireContext()).apply {
            setContent {
                AppTheme {
                    CreateExerciseScreen(
                        viewModel = exerciseDetailsViewModel
                    ) {
                        findNavController().popBackStack()
                    }
                }
            }
        }
    }
}