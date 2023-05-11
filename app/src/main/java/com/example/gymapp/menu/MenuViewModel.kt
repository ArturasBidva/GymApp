package com.example.gymapp.menu

import androidx.lifecycle.ViewModel
import com.example.gymapp.repository.MyRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MenuViewModel @Inject constructor(
    private val repository: MyRepository

) : ViewModel() {

}
