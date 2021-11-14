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

    @Query("SELECT * from days WHERE month = :month AND year = :year")
    fun getDaysOfMonthOfYear(month:Int,year: Int): Flow<List<Day>>

    @Query("SELECT DISTINCT month from days WHERE year = :year")
    fun getMonthsOfYear(year:Int): Flow<List<Int>>

    @Query("SELECT DISTINCT month FROM days")
    fun getMonths(): Flow<List<Int>>

    @Query("SELECT DISTINCT year FROM days")
    fun getYears(): Flow<List<Int>>

    @Query("SELECT EXISTS(SELECT currentDay FROM days WHERE currentDay = :currentDay)")
    fun isExists(currentDay: String):Flow<Boolean>


}