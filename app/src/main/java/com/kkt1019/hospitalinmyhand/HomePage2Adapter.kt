package com.kkt1019.hospitalinmyhand

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class HomePage2Adapter constructor(val context2:Context, var page2Items:MutableList<HomePage2Item>): RecyclerView.Adapter<HomePage2Adapter.VH>() {

    inner class VH(itemView: View): RecyclerView.ViewHolder(itemView){

        val tvTitle : TextView by lazy { itemView.findViewById(R.id.pg2_title) }
        val tvAddr : TextView by lazy { itemView.findViewById(R.id.pg2_address) }
        val tvTell : TextView by lazy { itemView.findViewById(R.id.pg2_tell) }
        val tvTell2 : TextView by lazy { itemView.findViewById(R.id.pg2_tell2) }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {

        val inflater: LayoutInflater = LayoutInflater.from(context2)
        val itemView = inflater.inflate(R.layout.recycler_homepage2_item, parent, false)

        return VH(itemView)

    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        val item = page2Items.get(position)

        holder.tvTitle.setText(item.dutyName)
        holder.tvAddr.setText(item.dutyAddr)
        holder.tvTell.setText(item.dutyTel1)
        holder.tvTell2.setText(item.dutyTel3)
    }



    override fun getItemCount(): Int = page2Items.size
}