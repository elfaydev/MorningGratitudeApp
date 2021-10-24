package com.elfay.powerfulthree.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "days")
data class Day(
    @PrimaryKey(autoGenerate = false)
    val currentDay: Long,
    @ColumnInfo(name="focus_on")
    val focusOn: String
)