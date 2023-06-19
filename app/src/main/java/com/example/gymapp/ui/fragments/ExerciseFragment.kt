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
import com.example.gymapp.exercises.ExerciseFragmentDirections
import com.example.gymapp.exercises.ExerciseScreen
import com.example.gymapp.ui.AppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ExerciseFragment : Fragment() {
    private val exerciseDetailsViewModel: ExerciseDetailsViewModel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                AppTheme {
                    ExerciseScreen(viewModel = exerciseDetailsViewModel, onExerciseClick = {
                        val action =
                            ExerciseFragmentDirections.actionExerciseFragmentToExerciseDetailsFragment(it)

                        findNavController().navigate(action)
                    })
                }
            }
        }
    }
}

