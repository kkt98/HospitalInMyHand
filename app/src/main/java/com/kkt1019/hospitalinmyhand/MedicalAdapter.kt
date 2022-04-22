package com.kkt1019.hospitalinmyhand

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class MedicalAdapter constructor(val context:Context, var medicalItem:MutableList<MedicalItem>) : RecyclerView.Adapter<MedicalAdapter.VH>() {

    inner class VH(itemView: View): RecyclerView.ViewHolder(itemView){

        val ivImage : ImageView by lazy { itemView.findViewById(R.id.image) }
        val tvMaker : TextView by lazy { itemView.findViewById(R.id.maker) }
        val tvName : TextView by lazy { itemView.findViewById(R.id.name) }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {

        val inflater : LayoutInflater = LayoutInflater.from(context)
        val itemView = inflater.inflate(R.layout.recycler_medical_item, parent, false)

        return VH(itemView)

    }

    override fun onBindViewHolder(holder: VH, position: Int) {

        val item = medicalItem.get(position)

        holder.ivImage.setImageResource(item.itemImage)
        holder.tvMaker.setText(item.entpName)
        holder.tvName.setText(item.itemName)

    }

    override fun getItemCount(): Int = medicalItem.size
}