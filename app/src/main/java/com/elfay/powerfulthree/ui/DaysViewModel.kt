package com.elfay.powerfulthree

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.elfay.powerfulthree.data.Day
import com.elfay.powerfulthree.data.DayDao
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class DaysViewModel(private val dayDao: DayDao) : ViewModel() {

    companion object {
        //oneday in millisecoands
        const val OFFSET = 86400000
        var nonFormatedDate: Long = 0
    }

    init {
        setDate()
    }

    private fun insertDay(day: Day) {
        viewModelScope.launch {
            dayDao.insert(day)
        }
    }

    //currentDay for this method is gonna be always the nonFormatedDate
    private fun getNewDayEntry(currentDay: Long, focusOn: String): Day {
        return Day(
            currentDay = currentDay,
            focusOn = focusOn
        )
    }

    fun addNewDay(currentDay: Long, focusOn: String) {
        val newDay = getNewDayEntry(currentDay, focusOn)
        insertDay(newDay)
    }

    fun setDate() {

        var calendar: Calendar
        calendar = Calendar.getInstance()
        nonFormatedDate = calendar.timeInMillis
    }

    fun formatDate(): String {

        var simpleDateFormat: SimpleDateFormat
        simpleDateFormat = SimpleDateFormat("EEEE\nLLLL dd'th' yyyy ")
        return simpleDateFormat.format(nonFormatedDate).toString()

    }

    fun isEntryValid(focusOn: String):Boolean{
        if(focusOn.isBlank()){
            return false
        }
        return true
    }
}


class DaysViewModelFactory(private val dayDao: DayDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DaysViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return DaysViewModel(dayDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }


}