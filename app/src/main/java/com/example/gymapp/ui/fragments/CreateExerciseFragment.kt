package com.example.gymapp.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import com.example.gymapp.ui.screens.createexercise.CreateExerciseScreen
import com.example.gymapp.ui.screens.exercise.ExerciseViewModel
import com.example.gymapp.ui.AppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CreateExerciseFragment : Fragment() {
    private val exerciseViewModel: ExerciseViewModel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                AppTheme {
                    CreateExerciseScreen(
                        viewModel = exerciseViewModel
                    ) {
                        findNavController().popBackStack()
                    }
                }
            }
        }
    }
}