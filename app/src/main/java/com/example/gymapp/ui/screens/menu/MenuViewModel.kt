package com.example.gymapp.ui.screens.menu

import androidx.lifecycle.ViewModel
import com.example.gymapp.data.repositories.MyRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MenuViewModel @Inject constructor(
    private val repository: MyRepository

) : ViewModel() {

}
