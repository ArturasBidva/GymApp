package com.example.gymapp.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Exercise(
    val id: Long,
    val title: String,
    val weight: Int = 0,
    val imgUrl: String,
    val description: String
) : Parcelable {
    constructor(
        title: String,
        weight: Int = 0,
        imgUrl: String,
        description: String
    ) : this(0L, title, weight, imgUrl, description)
}