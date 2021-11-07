package com.elfay.powerfulthree.adapter

import android.content.ClipData
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.elfay.powerfulthree.data.Day
import com.elfay.powerfulthree.databinding.CalendarListItemBinding
import java.text.FieldPosition

class CalendarListAdapter(private val onDayClicked:(Day) -> Unit,private val onDeleteClicked:(Day) -> Unit) :
    ListAdapter<Day,CalendarListAdapter.DayViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DayViewHolder{
        return DayViewHolder(
            CalendarListItemBinding.inflate(
                LayoutInflater.from(
                    parent.context
                )
            )
        )
    }

    override fun onBindViewHolder(holder: DayViewHolder, position: Int) {
        val current = getItem(position)
        holder.card.setOnClickListener {
            onDayClicked(current)
        }
        holder.delete.setOnClickListener {
            onDeleteClicked(current)


        }
        holder.bind(current)
    }

    class DayViewHolder(private var binding: CalendarListItemBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(day: Day) {
            binding.apply {
               mdate.text = "${cutString(day.currentDay)}\n\tth"
               if(day.focusOn.length>15){
                   thumbnail.text = "${day.focusOn.subSequence(0,15)}..."
               } else
                   thumbnail.text = day.focusOn
            }
        }
        val delete = binding.delete
        val card = binding.cardview
        private fun cutString(date: String): String{
            var i: Int =0
           for( j in 0..date.length-1){
                if(date[j] == ' ' ){
                    i = j
                    break

                }
            }

            return date.subSequence(i,i+3).toString()
        }
    }

    companion object{
        private val DiffCallback = object : DiffUtil.ItemCallback<Day>(){
            override fun areContentsTheSame(oldItem: Day, newItem: Day): Boolean {
                return oldItem == newItem
            }

            override fun areItemsTheSame(oldItem: Day, newItem: Day): Boolean {
                return oldItem.currentDay == newItem.currentDay
            }



        }
    }


}