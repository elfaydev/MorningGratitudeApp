package com.elfay.powerfulthree

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.elfay.powerfulthree.databinding.ActivityMainBinding
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

      /*  var dateTime: String
        var calendar: Calendar
        var simpleDateFormat: SimpleDateFormat

        calendar = Calendar.getInstance()
        simpleDateFormat = SimpleDateFormat("EEEE\nLLLL dd'th' yyyy ")
        //that constant number is oneday in millisecoands
        val you =calendar.timeInMillis+86400000
        dateTime = simpleDateFormat.format(you).toString()
        binding.date.text = dateTime.toString()*/


    }
}