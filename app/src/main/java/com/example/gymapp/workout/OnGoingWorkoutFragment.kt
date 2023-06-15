package com.example.gymapp.workout

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.navArgs

class OnGoingWorkoutFragment : Fragment() {
    private val workoutViewModel: WorkoutViewModel by activityViewModels()
    private val args: OnGoingWorkoutFragmentArgs by navArgs()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return ComposeView(requireContext()).apply {
            workoutViewModel.getWorkoutById(args.workoutId)
            setContent {
                OnGoingWorkout(workoutViewModel = workoutViewModel)
            }
        }
    }
}