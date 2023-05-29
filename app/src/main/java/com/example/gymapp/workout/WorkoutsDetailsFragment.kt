package com.example.gymapp.workout

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
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
                MaterialTheme {
                    WorkoutsDetailsScreen(viewModel = workoutDetailsViewModel, onIconClick = {
                     val action = WorkoutsDetailsFragmentDirections.actionWorkoutsDetailsFragmentToWorkoutDetailFragment(it)
                     findNavController().navigate(action)
                    })
                }
            }
        }
    }
}