package com.example.gymapp.ui.screens.exerciseedit

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.gymapp.ui.screens.exercise.ExerciseViewModel
import com.example.gymapp.ui.AppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ExerciseEditFragment : Fragment() {
    private val exerciseViewModel: ExerciseViewModel by activityViewModels()
    private val args: ExerciseEditFragmentArgs by navArgs()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                AppTheme {
                    ExerciseEditScreen(
                        viewModel = exerciseViewModel,
                        exercise = args.exercise,
                        onGoBack = { findNavController().popBackStack() }
                    )
                }
            }
        }
    }
}