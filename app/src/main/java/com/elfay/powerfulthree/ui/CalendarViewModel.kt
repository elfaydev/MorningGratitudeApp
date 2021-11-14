package com.elfay.powerfulthree.ui

import android.content.Context
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.*
import com.elfay.powerfulthree.data.Day
import com.elfay.powerfulthree.data.DayDao
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class CalendarViewModel(private val dayDao: DayDao) : ViewModel() {

    val allYears : LiveData<List<Int>> = dayDao.getYears().asLiveData()
    val allMonths : LiveData<List<Int>> = dayDao.getMonths().asLiveData()

    val indexOfYear = MutableLiveData<Int>(0)
    val indexOfMonth = MutableLiveData<Int>(0)

    var sizeOfYearsList : Int = 0
    var sizeOfMonthsList : Int = 0
    var sizeOfDaysList : Int = 0
    val MONTHS  = mapOf(0 to "Empty",1 to "January", 2 to "Febuary", 3 to "Mars", 4 to "Avril", 5 to "May", 6 to "June", 7 to "July", 8 to " August", 9 to "September", 10 to "October", 11 to "November", 12 to "December")
    fun retrieveDaysOfMonth(month:Int,year: Int): LiveData<List<Day>>{
        return dayDao.getDaysOfMonthOfYear(month,year).asLiveData()
    }

    fun retrieveMonthsOfYear(year:Int): LiveData<List<Int>>{
        return dayDao.getMonthsOfYear(year).asLiveData()
    }
    fun nextYear(){
        if(indexOfYear.value!! < (allYears.value?.size!!).minus(1)){
            indexOfYear.value = (indexOfYear.value)?.plus(1)
        }
    }
    fun previousYear(){
        if(indexOfYear.value!! > 0){
            indexOfYear.value = (indexOfYear.value)?.minus(1)
        }
    }

  fun nextMonth(){
      if(indexOfMonth.value!! < (sizeOfMonthsList).minus(1)){
          indexOfMonth.value = (indexOfMonth.value)?.plus(1)
      }
  }
    fun previousMonth(){
        if(indexOfMonth.value!! > 0){
            indexOfMonth.value = (indexOfMonth.value)?.minus(1)
        }
    }
    fun getMonth(): Int {
        var simpleDateFormat: SimpleDateFormat
        simpleDateFormat = SimpleDateFormat("LL")
        return simpleDateFormat.format(Calendar.getInstance().timeInMillis).toString().toInt()
    }
    fun deleteItem(day: Day){
        viewModelScope.launch {
            if(sizeOfDaysList==1){
                if(sizeOfMonthsList > 1){
                    indexOfMonth.value = (indexOfMonth.value)?.minus(1)
                    Log.d("ShowDataFragment","holle")

                } else{

                    indexOfYear.value = (indexOfYear.value)?.minus(1)
                }


            }
            dayDao.delete(day)
        }
    }

    fun update(){
        indexOfYear.value?.plus(1)?.minus(1)
    }

    fun resetIndexOfMonth() {
        indexOfMonth.value = 0
    }
}




class CalendarViewModelFactory(private val dayDao: DayDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CalendarViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return CalendarViewModel(dayDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }


}