package com.elfay.powerfulthree.ui


import android.os.Bundle

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.elfay.powerfulthree.DaysApplication
import com.elfay.powerfulthree.R
import com.elfay.powerfulthree.data.Day
import com.elfay.powerfulthree.data.PassDayToDialog
import com.elfay.powerfulthree.databinding.FragmentDataOfOneDayBinding


class DataOfOneDayFragment : DialogFragment() {


    private var _binding: FragmentDataOfOneDayBinding? = null
    private val binding get() = _binding!!
    lateinit var day: Day
    private val viewModel: DaysViewModel by activityViewModels {
        DaysViewModelFactory(
            (activity?.application as DaysApplication).database
                .dayDao()
        )
    }


    override fun getTheme() = R.style.RoundedCornersDialog
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        // dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        _binding = FragmentDataOfOneDayBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.retrieveDay(PassDayToDialog.day.currentDay)
            .observe(this.viewLifecycleOwner) { selectedday ->
                if (selectedday != null) {
                    day = selectedday
                    bind(day)
                }
            }


    }

    private fun bind(day: Day) {
        binding.apply {
            binding.dayClicked.text = day.currentDay
            binding.FocusDetails.text =day.focusOn
            binding.gratefulDetails.text =day.gratefulFor
            binding.LetGoOfDetails.text =day.letGoOf
        }
    }
}