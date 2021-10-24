package com.elfay.powerfulthree.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.elfay.powerfulthree.databinding.FragmentDaysBinding
import com.elfay.powerfulthree.DaysApplication
import com.elfay.powerfulthree.DaysViewModel
import com.elfay.powerfulthree.DaysViewModelFactory
import com.elfay.powerfulthree.DaysViewModel.Companion.OFFSET
import com.elfay.powerfulthree.DaysViewModel.Companion.nonFormatedDate
import com.elfay.powerfulthree.data.Day


class DaysFragment : Fragment() {

    //reusable code
    private val viewModel: DaysViewModel by activityViewModels {
        DaysViewModelFactory(
            (activity?.application as DaysApplication).database
                .dayDao()
        )
    }
    lateinit var day: Day
    private var _binding: FragmentDaysBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDaysBinding.inflate(inflater, container, false)
        return binding.root

    }

    private  fun  isEntryValid(): Boolean{
        return viewModel.isEntryValid(
            binding.focuson.text.toString()
        )
    }
    private fun addDay(){
        if (isEntryValid()){
            viewModel.addNewDay(nonFormatedDate,binding.focuson.text.toString())
        }
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.date.text = viewModel.formatDate()


        binding.left.setOnClickListener {
            nonFormatedDate -= OFFSET
            binding.date.text = viewModel.formatDate()
        }
        binding.right.setOnClickListener {
            nonFormatedDate += OFFSET
            binding.date.text = viewModel.formatDate()
        }

        binding.button.setOnClickListener{
            addDay()
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}