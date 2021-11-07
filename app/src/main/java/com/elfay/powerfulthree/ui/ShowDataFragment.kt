package com.elfay.powerfulthree.ui

import android.os.Bundle
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
            { PassDayToDialog.day=it
                DataOfOneDayFragment().show(childFragmentManager,"hello")},{ showConfirmationDialog(it)})


        binding.calendarRecyclerView.adapter = adapter



        viewModel.indexOfMonth.observe(viewLifecycleOwner) {
            viewModel.allMonths.observe(this.viewLifecycleOwner) {
                if (it.isNotEmpty()) {
                    binding.emptyCalendar.isVisible=false
                    binding.date.text = it.get(viewModel.indexOfMonth.value!!)
                    viewModel.retrieveDaysOfMonth(it.get(viewModel.indexOfMonth.value!!))
                        .observe(this.viewLifecycleOwner) { days ->
                            days.let {
                                adapter.submitList(it)
                            }
                        }
                }
                else{
                    binding.emptyCalendar.isVisible=true
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
    }

    private fun showConfirmationDialog(day: Day) {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Delete item!")
            .setMessage("Are you sure you want to delete this item?")
            .setCancelable(false)
            .setNegativeButton("No") { _, _ -> }
            .setPositiveButton("Yes") { _, _ -> viewModel.deleteItem(day) }
            .show()
    }
    /*private fun deleteItem(day: Day){
        viewModel.deleteItem(day)
    }*/

}