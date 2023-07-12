package com.example.notes_taskapp

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class TaskEntites(
    @PrimaryKey(autoGenerate = true)
    var id : Int = 0,
    @ColumnInfo()
    var Task1 : String? = null

)
