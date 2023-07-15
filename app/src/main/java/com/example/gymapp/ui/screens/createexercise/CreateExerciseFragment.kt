package com.example.gymapp.ui.screens.createexercise

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import com.example.gymapp.ui.AppTheme
import com.example.gymapp.ui.screens.exercise.CreateExerciseViewModel
import com.example.gymapp.ui.screens.exercise.ExerciseViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CreateExerciseFragment : Fragment() {
    private val exerciseViewModel: ExerciseViewModel by activityViewModels()
    private val createExerciseViewModel: CreateExerciseViewModel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                AppTheme {
                    val state by exerciseViewModel.uiState.collectAsState()
                    val snackbarHostState = remember { SnackbarHostState() }

                    CreateExerciseScreen(
                        viewModel = exerciseViewModel,
                        createExerciseViewModel = createExerciseViewModel,
                        onNavigateBack = { findNavController().popBackStack() },
                        onEvent = exerciseViewModel::onEvent,
                        state = state,
                        snackbarHostState = snackbarHostState
                    )
                }
            }
        }
    }
}