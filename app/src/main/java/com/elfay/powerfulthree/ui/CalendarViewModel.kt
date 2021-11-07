package com.elfay.powerfulthree.ui

import androidx.lifecycle.*
import com.elfay.powerfulthree.data.Day
import com.elfay.powerfulthree.data.DayDao
import kotlinx.coroutines.launch

class CalendarViewModel(private val dayDao: DayDao) : ViewModel() {


    val allMonths : LiveData<List<String>> = dayDao.getMonths().asLiveData()

    val indexOfMonth = MutableLiveData<Int>(0)


    fun retrieveDaysOfMonth(month:String): LiveData<List<Day>>{
        return dayDao.getDaysOfMonth(month).asLiveData()
    }


  fun nextMonth(){
      if(indexOfMonth.value!! < (allMonths.value?.size!!).minus(1)){
          indexOfMonth.value = (indexOfMonth.value)?.plus(1)
      }
  }
    fun previousMonth(){
        if(indexOfMonth.value!! > 0){
            indexOfMonth.value = (indexOfMonth.value)?.minus(1)
        }
    }

    fun deleteItem(day: Day){
        viewModelScope.launch {
            dayDao.delete(day)
        }
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