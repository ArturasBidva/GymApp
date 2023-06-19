package com.example.gymapp.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.gymapp.exercises.ExerciseDetailsFragmentArgs
import com.example.gymapp.exercises.ExerciseDetailsFragmentDirections
import com.example.gymapp.exercises.ExerciseDetailsScreen
import com.example.gymapp.exercises.ExerciseDetailsViewModel
import com.example.gymapp.ui.AppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ExerciseDetailsFragment : Fragment() {
    private val exerciseDetailsViewModel: ExerciseDetailsViewModel by activityViewModels()
    private val args: ExerciseDetailsFragmentArgs by navArgs()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        exerciseDetailsViewModel.getExerciseById(args.exerciseId)
        return ComposeView(requireContext()).apply {
            setContent {
                AppTheme {
                    ExerciseDetailsScreen(
                        exerciseDetailsViewModel = exerciseDetailsViewModel,
                        onDeleteExerciseClick = {
                            exerciseDetailsViewModel.deleteExerciseById(it.id) {
                                findNavController().popBackStack()
                            }

                        }, onUpdateExerciseClick = {
                            val action =
                                ExerciseDetailsFragmentDirections.actionExerciseDetailsFragmentToExerciseEditFragment(
                                    it
                                )
                            findNavController().navigate(action)
                        })
                }
            }
        }
    }
}