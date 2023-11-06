package com.example.gymapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.gymapp.ui.screens.exercise.ExerciseViewModel
import com.example.gymapp.ui.screens.workout.WorkoutViewModel
import com.example.gymapp.ui.screens.workoutschedule.WorkoutScheduleViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var viewModel: ExerciseViewModel
    private lateinit var workoutViewModel: WorkoutViewModel
    private lateinit var fab: FloatingActionButton
    private lateinit var bottomNavigationView: BottomNavigationView
    private lateinit var navController: NavController
    private lateinit var workoutScheduleViewModel: WorkoutScheduleViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        bottomNavigationView = findViewById(R.id.bottomNavigationView)
        fab = findViewById(R.id.fab)
        workoutScheduleViewModel = ViewModelProvider(this)[WorkoutScheduleViewModel::class.java]
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.my_nav)
        fab.setOnClickListener { workoutScheduleViewModel.setWorkoutScheduleDialogVisibility(visible = true) }

        if (navHostFragment != null) {
            navHostFragment.findNavController()
            navController = navHostFragment.findNavController()
            bottomNavigationView.setupWithNavController(navController)

            bottomNavigationView.setOnItemSelectedListener { item ->
                if (item.itemId != bottomNavigationView.selectedItemId) {
                    navController.navigate(item.itemId)
                } else {
                    navController.popBackStack(item.itemId, false)
                }
                true
            }
            navController.addOnDestinationChangedListener { _, destination, _ ->
                when (destination.id) {
                    R.id.workoutSchedule -> fab.show()
                    else -> fab.hide()
                }
            }
        }
    }
}