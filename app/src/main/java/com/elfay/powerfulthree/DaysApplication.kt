package com.elfay.powerfulthree


import android.app.Application
import com.elfay.powerfulthree.data.DayRoomDatabase


class DaysApplication : Application() {
    // Using by lazy so the database and the repository are only created when they're needed
    // rather than when the application starts
    val database: DayRoomDatabase by lazy { DayRoomDatabase.getDatabase(this) }
}
