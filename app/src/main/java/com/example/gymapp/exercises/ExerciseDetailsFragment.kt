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
import okhttp3.internal.wait

@AndroidEntryPoint
class ExerciseDetailsFragment : Fragment() {
    private val exerciseDetailsViewModel: ExerciseDetailsViewModel by activityViewModels()
    private val args: ExerciseDetailsFragmentArgs by navArgs()
    private val exerciseViewModel: ExerciseViewModel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        exerciseDetailsViewModel.getExerciseById(args.exerciseId)
        return ComposeView(requireContext()).apply {
            setContent {
                MaterialTheme {
                    ExerciseDetailsScreen(
                        viewModel = exerciseViewModel,
                        exerciseDetailsViewModel = exerciseDetailsViewModel,
                        onDeleteExerciseClick = {
                           exerciseDetailsViewModel.deleteExerciseById(it.id){
                               findNavController().popBackStack()
                           }

                        }
                     , onUpdateExerciseClick = {
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