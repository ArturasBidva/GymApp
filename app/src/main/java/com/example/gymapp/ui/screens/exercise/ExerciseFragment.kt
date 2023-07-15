package com.example.gymapp.ui.screens.exercise

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import com.example.gymapp.ui.AppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ExerciseFragment : Fragment() {
    private val exerciseViewModel: ExerciseViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        return ComposeView(requireContext()).apply {
            setContent {
                AppTheme {
                    val snackbarHostState = remember { SnackbarHostState() }
                    ExerciseScreen(
                        viewModel = exerciseViewModel,
                        onExerciseClick = {
                        val action =
                            ExerciseFragmentDirections
                                .actionExerciseFragmentToExerciseDetailsFragment(it)
                        findNavController().navigate(action)
                    }, onAddExerciseButtonClick = {
                        val action =
                            ExerciseFragmentDirections
                                .actionExerciseFragmentToCreateExerciseFragment()
                        findNavController().navigate(action)
                    }, snackbarHostState = snackbarHostState)
                }
            }
        }
    }
}