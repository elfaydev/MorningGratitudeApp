package com.elfay.powerfulthree.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface DayDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(day:Day)

    @Update
    suspend fun update(day: Day)

    @Delete
    suspend fun delete(day: Day)

    @Query("SELECT * from days WHERE currentDay=:currentDay")
    fun getItem(currentDay:Long): Flow<Day>


}