package com.example.gymapp.ui.screens.createworkout

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import com.example.gymapp.ui.AppTheme
import com.example.gymapp.ui.screens.workout.WorkoutViewModel

class CreateWorkoutFragment : Fragment() {

    private val workoutViewModel: WorkoutViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                AppTheme {
                    CreateWorkoutScreen(
                        workoutViewModel = workoutViewModel,
                        onBackClick = {
                            findNavController().popBackStack()
                        },
                        onAddClick = {
                            val action =
                                CreateWorkoutFragmentDirections.actionCreateWorkoutFragmentToExerciseFragment()
                            findNavController().navigate(action)
                        }
                    )
                }
            }
        }
    }
}