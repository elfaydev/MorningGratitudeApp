package com.elfay.powerfulthree.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.elfay.powerfulthree.DaysApplication
import com.elfay.powerfulthree.R
import com.elfay.powerfulthree.adapter.CalendarListAdapter
import com.elfay.powerfulthree.data.Day
import com.elfay.powerfulthree.data.PassDayToDialog
import com.elfay.powerfulthree.databinding.FragmentDaysBinding
import com.elfay.powerfulthree.databinding.FragmentShowDataBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder


class ShowDataFragment : Fragment() {


    private val viewModel: CalendarViewModel by activityViewModels {
        CalendarViewModelFactory(
            (activity?.application as DaysApplication).database
                .dayDao()
        )
    }

    private var _binding: FragmentShowDataBinding? = null
    private val binding get() = _binding!!

    private var list: List<Int>? = null
    private var monthsList: List<Int>? = null
    private var year : Int = 0
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentShowDataBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = CalendarListAdapter(
            {
                PassDayToDialog.day = it
                DataOfOneDayFragment().show(childFragmentManager, "hello")
            }, { showConfirmationDialog(it) })


        binding.calendarRecyclerView.adapter = adapter


        //to show the currentMonth in the calendarFragment i should assign the indexOfMonth with the index of the currentMonth not with 0
        /*val currentMonth = viewModel.getMonth()
        if(viewModel.allMonths.value?.contains(currentMonth) )
        val index = viewModel.allMonths.value?.indexOf(currentMonth)
        viewModel.indexOfMonth.value = index*/


       /* viewModel.indexOfMonth.observe(viewLifecycleOwner) {
            viewModel.allMonths.observe(this.viewLifecycleOwner) {
                if (it.isNotEmpty()) {
                    binding.emptyCalendar.isVisible = false
                    val sortedMonthsList = it.sorted()
                    viewModel.sizeOfMonthsList = it.size
                    binding.date.text = viewModel.MONTHS[sortedMonthsList[viewModel.indexOfMonth.value!!]]
                    viewModel.retrieveDaysOfMonth(sortedMonthsList[viewModel.indexOfMonth.value!!])
                        .observe(this.viewLifecycleOwner) { days ->
                            days.let {
                                viewModel.sizeOfDaysList = it.size
                                    adapter.submitList(it)

                            }
                        }
                } else {
                    binding.emptyCalendar.isVisible = true
                }

            }

        }*/


          viewModel.indexOfYear.observe(viewLifecycleOwner) {
                viewModel.resetIndexOfMonth()
              viewModel.allYears.observe(this.viewLifecycleOwner) { it ->
                  if (it.isNotEmpty()) {
                      binding.emptyCalendar.isVisible=false
                       val sortedYears =it.sorted()
                      viewModel.sizeOfYearsList = sortedYears.size

                      year = sortedYears[viewModel.indexOfYear.value!!] //// here an IndexOutOfBound error happens when i delete a year because the indexOfyear doesn't decrase with 1
                      binding.year.text = year.toString()

                    viewModel.retrieveMonthsOfYear(sortedYears[viewModel.indexOfYear.value!!]).observe(this.viewLifecycleOwner) { monthList ->
                              val sortedMonthsList =monthList.sorted()
                                viewModel.sizeOfMonthsList = sortedMonthsList.size
                                 monthsList = sortedMonthsList
                        if (monthList.isNotEmpty()) {
                            binding.month.text =
                                viewModel.MONTHS[sortedMonthsList[viewModel.indexOfMonth.value!!]]

                            viewModel.retrieveDaysOfMonth(
                                sortedMonthsList[viewModel.indexOfMonth.value!!],
                                year
                            ).observe(this.viewLifecycleOwner) { days ->
                                days.let {
                                    adapter.submitList(it)
                                }
                            }
                        } else{
                            //binding.emptyCalendar.isVisible=true
                        }

                          }
                  }
                  else{
                      binding.emptyCalendar.isVisible=true
                  }

              }

          }
          /*viewModel.indexOfMonth.observe(viewLifecycleOwner) {
              sortedYearsList?.get(viewModel.indexOfYear.value!!)?.let {year ->
                  viewModel.retrieveMonthsOfYear(year)
                      .observe(this.viewLifecycleOwner) {monthList ->
                          viewModel.sizeOfMonthsList = monthList.size
                          val sortedMonthsList =monthList.sorted()

                          //list = sortedMonthsList
                          binding.date.text =
                              viewModel.MONTHS[sortedMonthsList[viewModel.indexOfMonth.value!!]]
                          viewModel.retrieveDaysOfMonth(sortedMonthsList[viewModel.indexOfMonth.value!!])
                              .observe(this.viewLifecycleOwner) { days ->
                                  days.let {
                                      viewModel.sizeOfDaysList = it.size
                                      adapter.submitList(it)
                                  }
                              }}
              }
          }*/



       viewModel.indexOfMonth.observe(viewLifecycleOwner) {
                   //  binding.emptyCalendar.isVisible=false
                     binding.month.text = viewModel.MONTHS.get(monthsList?.get(viewModel.indexOfMonth.value!!))
           monthsList?.get(viewModel.indexOfMonth.value!!)?.let { month ->
                viewModel.retrieveDaysOfMonth(month,year)
                    .observe(this.viewLifecycleOwner) { days ->
                        days.let {
                            viewModel.sizeOfDaysList = it.size
                            adapter.submitList(it)
                        }
                    }
            }

             }

        binding.calendarRecyclerView.layoutManager =
            GridLayoutManager(this.requireContext(), 2, LinearLayoutManager.VERTICAL, false)

        binding.left.setOnClickListener {
            viewModel.previousMonth()
        }
        binding.right.setOnClickListener {
            viewModel.nextMonth()
        }

        binding.yearleft.setOnClickListener {
            viewModel.previousYear()
        }
        binding.yearright.setOnClickListener {
            viewModel.nextYear()
        }
    }

    private fun showConfirmationDialog(day: Day) {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Delete item!")
            .setMessage("Are you sure you want to delete this item?")
            .setCancelable(false)
            .setNegativeButton("No") { _, _ -> }
            .setPositiveButton("Yes") { _, _ ->

                viewModel.deleteItem(day) }.show()
    }
    /*private fun deleteItem(day: Day){
        viewModel.deleteItem(day)
    }*/

}