package com.example.gymapp.exercises

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ExerciseEditFragment : Fragment() {
    private val exerciseViewModel: ExerciseViewModel by activityViewModels()
    private val exerciseDetailsViewModel: ExerciseDetailsViewModel by activityViewModels()
    private val args: ExerciseEditFragmentArgs by navArgs()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                MaterialTheme {
                    ExerciseEditScreen(
                        viewModel = exerciseDetailsViewModel,
                        exercise = args.exercise,
                        onGoBack = { findNavController().popBackStack() })
                }
            }
        }
    }
}