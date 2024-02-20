package com.kkt1019.hospitalinmyhand.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.kkt1019.hospitalinmyhand.R
import com.kkt1019.hospitalinmyhand.roomdata.CalendarEntity

class CalendarAdapter(val context: Context, var calendarItems: MutableList<CalendarEntity>) : RecyclerView.Adapter<CalendarAdapter.VH>() {

    inner class VH(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvSchedule: TextView by lazy { itemView.findViewById(R.id.my_schedule) }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val inflater: LayoutInflater = LayoutInflater.from(context)
        val itemView = inflater.inflate(R.layout.recycler_calendar_item, parent, false)
        return VH(itemView)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        val item = calendarItems[position]
        // CalendarEntity의 content를 표시
        holder.tvSchedule.text = item.content
    }

    override fun getItemCount(): Int = calendarItems.size

    // 데이터 세트 변경 시 사용할 함수
    fun updateItems(newItems: List<CalendarEntity>) {
        calendarItems.clear()
        calendarItems.addAll(newItems)
        notifyDataSetChanged()
    }
}