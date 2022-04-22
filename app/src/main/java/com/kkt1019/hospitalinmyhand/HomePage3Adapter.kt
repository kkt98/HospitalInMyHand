package com.kkt1019.hospitalinmyhand

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class HomePage3Adapter constructor(val context:Context, var page3Items: MutableList<HomePage3Item>): RecyclerView.Adapter<HomePage3Adapter.VH>() {

    inner class VH(itemView: View):RecyclerView.ViewHolder(itemView){

        val tvTitle : TextView by lazy { itemView.findViewById(R.id.pg3_title) }
        val tvAddr : TextView by lazy { itemView.findViewById(R.id.pg3_address) }
        val tvTell : TextView by lazy { itemView.findViewById(R.id.pg3_tell) }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {

        val inflater:LayoutInflater = LayoutInflater.from(context)
        val itemView = inflater.inflate(R.layout.recycler_homepage3_item, parent, false)

        return VH(itemView)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        val item = page3Items.get(position)

        holder.tvTitle.setText(item.yadmNm)
        holder.tvAddr.setText(item.addr)
        holder.tvTell.setText(item.telno)
    }

    override fun getItemCount(): Int = page3Items.size
}