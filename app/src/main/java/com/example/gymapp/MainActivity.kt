package com.example.gymapp

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationBarView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity(),  NavigationBarView.OnItemSelectedListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val bottomNavigationView: BottomNavigationView = findViewById(R.id.bottomNavigationView)
        bottomNavigationView.setOnItemSelectedListener(this)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        val navController: NavController = Navigation.findNavController(this, R.id.fragmentContainerView)
        return when (item.itemId) {
            R.id.exerciseFragmentNav -> {
                navController.navigate(R.id.exerciseFragment)
                true
            }
            R.id.profileFragmentNav -> {
                navController.navigate(R.id.profileFragment)
                true
            }
            R.id.menuFragmentNav -> {
                navController.navigate(R.id.menuFragment)
                true
            }
            else -> false
        }
    }
}