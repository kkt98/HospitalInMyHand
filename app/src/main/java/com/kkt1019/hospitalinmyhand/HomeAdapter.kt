package com.kkt1019.hospitalinmyhand

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class HomeAdapter constructor(val context:Context, var items:MutableList<HomeItem>) : RecyclerView.Adapter<HomeAdapter.VH>(){

    inner class VH(itemView: View) : RecyclerView.ViewHolder(itemView){

        val tvTitle : TextView by lazy { itemView.findViewById(R.id.title) }
        val tvAddress : TextView by lazy { itemView.findViewById(R.id.address) }
        val tvTime : TextView by lazy { itemView.findViewById(R.id.time) }
        val tvTell : TextView by lazy { itemView.findViewById(R.id.tell) }
//        val ivLight : ImageView by lazy { itemView.findViewById(R.id.light) }
//        val ivFav : ImageView by lazy { itemView.findViewById(R.id.fav) }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val inflater: LayoutInflater = LayoutInflater.from(context)
        val itemView = inflater.inflate(R.layout.recycler_home_item, parent,false)

        return VH(itemView)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        val item = items.get(position)

        holder.tvTitle.setText(item.dutyName)
        holder.tvAddress.setText(item.dutyAddr)
        holder.tvTime.setText(item.dutyTime1c)
        holder.tvTell.setText(item.dutyTell)

    }

    override fun getItemCount(): Int = items.size
}