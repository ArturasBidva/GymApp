package com.example.gymapp.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import com.example.gymapp.ui.AppTheme
import com.example.gymapp.workout.WorkoutViewModel
import com.example.gymapp.workout.WorkoutsDetailsScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WorkoutsDetailsFragment : Fragment(

) {
    private val workoutDetailsViewModel: WorkoutViewModel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return ComposeView(requireContext()).apply {
            setContent {
                AppTheme {
                    WorkoutsDetailsScreen(viewModel = workoutDetailsViewModel, onIconClick = {
                     val action = WorkoutsDetailsFragmentDirections.actionWorkoutsDetailsFragmentToWorkoutDetailFragment(it)
                     findNavController().navigate(action)
                    })
                }
            }
        }
    }
}