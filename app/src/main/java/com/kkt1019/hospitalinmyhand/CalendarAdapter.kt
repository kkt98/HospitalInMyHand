package com.kkt1019.hospitalinmyhand

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView

class CalendarAdapter(val context: Context,  var calendaritems: MutableList<Calendar_Item>) : RecyclerView.Adapter<CalendarAdapter.VH>(){

    inner class VH(itemView: View) : RecyclerView.ViewHolder(itemView){

        val tvschedule : TextView by lazy { itemView.findViewById(R.id.my_schedule) }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val inflater: LayoutInflater = LayoutInflater.from(context)
        val itemView = inflater.inflate(R.layout.recycler_calendar_item, parent,false)

        return VH(itemView)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: VH, position: Int) {
        val item = calendaritems[position]

        holder.tvschedule.setText(calendaritems[position].schedule)

        holder.itemView.setOnClickListener {

        }

    }

    override fun getItemCount(): Int = calendaritems.size
}