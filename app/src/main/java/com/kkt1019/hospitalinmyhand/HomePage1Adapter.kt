package com.kkt1019.hospitalinmyhand

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class HomePage1Adapter constructor(val context:Context, var page1Items:MutableList<HomePage1Item>) : RecyclerView.Adapter<HomePage1Adapter.VH>(){

    inner class VH(itemView: View) : RecyclerView.ViewHolder(itemView){

        val tvTitle : TextView by lazy { itemView.findViewById(R.id.title) }
        val tvAddress : TextView by lazy { itemView.findViewById(R.id.address) }
        val tvTime : TextView by lazy { itemView.findViewById(R.id.time) }
        val tvTell : TextView by lazy { itemView.findViewById(R.id.tell) }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val inflater: LayoutInflater = LayoutInflater.from(context)
        val itemView = inflater.inflate(R.layout.recycler_homepage1_item, parent,false)

        return VH(itemView)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        val item = page1Items.get(position)

        holder.tvTitle.setText(item.dutyName)
        holder.tvAddress.setText(item.dutyAddr)
        holder.tvTime.setText(item.dutyTime1c)
        holder.tvTell.setText(item.dutyTell)

    }

    override fun getItemCount(): Int = page1Items.size
}