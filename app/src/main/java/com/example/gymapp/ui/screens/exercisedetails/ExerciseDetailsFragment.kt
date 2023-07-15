package com.example.gymapp.ui.screens.exercisedetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.gymapp.ui.AppTheme
import com.example.gymapp.ui.screens.exercise.ExerciseViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ExerciseDetailsFragment : Fragment() {
    private val exerciseViewModel: ExerciseViewModel by activityViewModels()
    private val args: ExerciseDetailsFragmentArgs by navArgs()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        exerciseViewModel.getExerciseById(args.exerciseId)
        return ComposeView(requireContext()).apply {
            setContent {
                AppTheme {
                    ExerciseDetailsScreen(
                        exerciseViewModel = exerciseViewModel,
                        onDeleteExerciseClick = {
                            exerciseViewModel.onEvent(
                                event = it,
                                onFetchComplete = {
                                    if(isAdded){
                                    findNavController().popBackStack()}
                                }
                            )
                        },
                        onUpdateExerciseClick = {
                            val action = ExerciseDetailsFragmentDirections
                                .actionExerciseDetailsFragmentToExerciseEditFragment(it)
                            findNavController().navigate(action)
                        },
                    )
                }
            }
        }
    }
}