package com.kkt1019.hospitalinmyhand

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.kkt1019.hospitalinmyhand.databinding.RecyclerMedicalItemBinding

class MedicalAdapter(val context:Context, var items: MutableList<MedicalItems>) : RecyclerView.Adapter<MedicalAdapter.VH>() {

    inner class VH(itemView: View): RecyclerView.ViewHolder(itemView){

        val binding:RecyclerMedicalItemBinding = RecyclerMedicalItemBinding.bind(itemView)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {

        val inflater : LayoutInflater = LayoutInflater.from(context)
        val itemView = inflater.inflate(R.layout.recycler_medical_item, parent, false)

        return VH(itemView)

    }

    override fun onBindViewHolder(holder: VH, position: Int) {

        holder.binding.maker.text = items[position].entpName
        holder.binding.name.text = items[position].itemName

        Glide.with(context).load(items[position].itemImage).into(holder.binding.image)

    }

    override fun getItemCount(): Int = items.size
}