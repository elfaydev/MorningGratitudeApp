package com.elfay.powerfulthree.ui


import android.app.DatePickerDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import android.widget.Toast.makeText
import androidx.fragment.app.activityViewModels
import com.elfay.powerfulthree.databinding.FragmentDaysBinding
import com.elfay.powerfulthree.DaysApplication
import com.elfay.powerfulthree.R
import com.elfay.powerfulthree.data.Day
import java.text.SimpleDateFormat
import java.util.*


class DaysFragment : Fragment() {


    lateinit var day: Day


    //reusable code
    private val viewModel: DaysViewModel by activityViewModels {
        DaysViewModelFactory(
            (activity?.application as DaysApplication).database
                .dayDao()
        )
    }

    private var _binding: FragmentDaysBinding? = null
    private val binding get() = _binding!!
    private val cal = Calendar.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDaysBinding.inflate(inflater, container, false)
        return binding.root

    }

    private fun isEntryValid(): Boolean {
        return viewModel.isEntryValid(
            binding.focuson.text.toString(),
            binding.gratefulfor.text.toString(),
            binding.letgoof.text.toString()
        )
    }

    private fun bind(day: Day) {
        binding.apply {
            //showData.text = "${day.focusOn}+${day.gratefulFor}+${day.letGoOf}"
            focuson.setText(day.focusOn, TextView.BufferType.SPANNABLE)
            gratefulfor.setText(day.gratefulFor, TextView.BufferType.SPANNABLE)
            letgoof.setText(day.letGoOf, TextView.BufferType.SPANNABLE)
        }
    }

    private fun emptyEditTexts() {
        binding.apply {
            focuson.setText("", TextView.BufferType.SPANNABLE)
            gratefulfor.setText("", TextView.BufferType.SPANNABLE)
            letgoof.setText("", TextView.BufferType.SPANNABLE)
        }
    }

    private fun addDay() {
        //before adding enter variable the toast("Item has been updated") keeps being displayed on screen whe adding and item fro the first time
        var enter: Int = 0
        if (isEntryValid()) {
            viewModel.isDayExists(viewModel.formatDate()).observe(viewLifecycleOwner) {

                if (!it) {
                    enter = 1
                    viewModel.addNewDay(
                        viewModel.formatDate(),
                        viewModel.getYear(),
                        viewModel.getMonth(),
                        binding.focuson.text.toString(),
                        binding.gratefulfor.text.toString(),
                        binding.letgoof.text.toString())
                    makeText(context, "Item has been added", Toast.LENGTH_SHORT).show()
                } else {
                    if (enter == 0) {
                        updateDay()
                        makeText(context, "Item has been updated", Toast.LENGTH_SHORT).show()
                        enter = 1
                    }

                }


            }

        }

    }

    private  fun updateDay(){
            viewModel.updateItem(
                viewModel.formatDate(),
                viewModel.getYear(),
                viewModel.getMonth(),
                binding.focuson.text.toString(),
                binding.gratefulfor.text.toString(),
                binding.letgoof.text.toString()
            )

    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.date.text = viewModel.formatDate()

        DaysViewModel.nonFormatedDate.observe(viewLifecycleOwner) {
            binding.date.text = viewModel.formatDate()
            viewModel.retrieveDay(viewModel.formatDate())
                .observe(this.viewLifecycleOwner) { selectedday ->
                    if (selectedday != null) {
                        day = selectedday
                        bind(day)
                    } else {
                        emptyEditTexts()
                    }
                }
        }


        val dateSetListener =  DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                cal.set(Calendar.YEAR, year)
               cal.set(Calendar.MONTH, monthOfYear)
                cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)

            val format = "dd.MM.yyyy"
            val sdf = SimpleDateFormat(format,Locale.US)
                binding.test.text = sdf.format(cal.time)
                viewModel.updateDate(cal.timeInMillis)
            }


        binding.date.setOnClickListener {
            DatePickerDialog(requireContext(),R.style.ThemeOverlay_App_DatePicker, dateSetListener,
                cal.get(Calendar.YEAR)
                ,cal.get(Calendar.MONTH)
                ,cal.get(Calendar.DAY_OF_MONTH)).show()
        }
        binding.left.setOnClickListener {
            viewModel.minusDay()

        }
        binding.right.setOnClickListener {
            viewModel.plusDay()

        }

        /*   viewModel.retrieveDay(viewModel.formatDate())
               .observe(this.viewLifecycleOwner) { selecteday ->
                   if (selecteday != null) {
                       day = selecteday
                       bind(day)
                   }
               }*/


        binding.button.setOnClickListener {
            addDay()
        }


    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}