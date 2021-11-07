package com.elfay.powerfulthree.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.elfay.powerfulthree.R
import com.elfay.powerfulthree.data.Quotes
import com.elfay.powerfulthree.databinding.FragmentDaysBinding
import com.elfay.powerfulthree.databinding.FragmentQuoteBinding
import java.text.SimpleDateFormat
import java.util.*


class QuoteFragment : Fragment() {

    private var _binding: FragmentQuoteBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentQuoteBinding.inflate(inflater, container, false)
        return binding.root
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        var current: Calendar
        current = Calendar.getInstance()

        var currentDate = current.timeInMillis


        var dayOfYear = getDayOfTheYear(currentDate)-1
        val NUMBER_OF_QUOTES = Quotes().quotes().size

        var result =dayOfYear/NUMBER_OF_QUOTES
        var index = dayOfYear - (result * NUMBER_OF_QUOTES)
        binding.quote.text = getString(Quotes().quotes().get(index).stringResourceId)
    }




    fun getDayOfTheYear(time: Long): Int {
        var simpleDateFormat: SimpleDateFormat
        simpleDateFormat = SimpleDateFormat("D")
        return simpleDateFormat.format(time).toString().toInt()
    }

}