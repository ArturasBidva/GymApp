package com.example.gymapp.data.db.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "amogs")
data class Testas(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(defaultValue = "1")
    val id: Int = 0, @ColumnInfo(defaultValue = "")
    val name: String = ""
) {

}