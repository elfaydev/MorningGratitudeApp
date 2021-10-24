package com.elfay.powerfulthree.ui

import androidx.lifecycle.ViewModel
import java.text.SimpleDateFormat
import java.util.*

class DateViewModel : ViewModel() {
    companion object {
        //oneday in millisecoands
        const val OFFSET = 86400000
        var nonFormatedDate: Long = 0
    }

    init {
        setDate()
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
}