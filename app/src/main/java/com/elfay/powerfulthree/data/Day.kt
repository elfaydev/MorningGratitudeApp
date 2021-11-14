package com.elfay.powerfulthree.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "days")
data class Day(
    @PrimaryKey(autoGenerate = false)
    val currentDay:String,
    @ColumnInfo(name = "year")
    val year: Int,
    @ColumnInfo(name = "month")
    val month: Int,
    @ColumnInfo(name = "focus_on")
    val focusOn: String,
    @ColumnInfo(name = "grateful_for")
    val gratefulFor: String,
    @ColumnInfo(name = "let_go_of")
    val letGoOf: String
)