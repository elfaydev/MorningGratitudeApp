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
    fun getDay(currentDay:String): Flow<Day>

    @Query("SELECT * from days WHERE month = :month")
    fun getDaysOfMonth(month: String): Flow<List<Day>>

    @Query("SELECT DISTINCT month FROM days")
    fun getMonths(): Flow<List<String>>

    @Query("SELECT EXISTS(SELECT currentDay FROM days WHERE currentDay = :currentDay)")
    fun isExists(currentDay: String):Flow<Boolean>


}