package com.elfay.powerfulthree.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

//this code can be used as a template for other projects that uses roomdatabase needs only to change the specific names.
@Database(entities = [Day::class], version = 2, exportSchema = false)
abstract class DayRoomDatabase : RoomDatabase() {

    abstract fun dayDao(): DayDao

    companion object {
        @Volatile
        private var INSTANCE: DayRoomDatabase? = null
        fun getDatabase(context: Context): DayRoomDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    DayRoomDatabase::class.java,
                    "day_database"

                ).fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                return instance
            }

        }
    }
}