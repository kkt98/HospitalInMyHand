package com.kkt1019.hospitalinmyhand.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kkt1019.hospitalinmyhand.data.MedicalItems
import com.kkt1019.hospitalinmyhand.R
import com.kkt1019.hospitalinmyhand.activity.MedicalMoreActivity
import com.kkt1019.hospitalinmyhand.databinding.RecyclerMedicalItemBinding

class MedicalAdapter(val context:Context, var items: List<MedicalItems>) : RecyclerView.Adapter<MedicalAdapter.VH>() {

    inner class VH(itemView: View): RecyclerView.ViewHolder(itemView){

        val binding:RecyclerMedicalItemBinding = RecyclerMedicalItemBinding.bind(itemView)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {

        val inflater : LayoutInflater = LayoutInflater.from(context)
        val itemView = inflater.inflate(R.layout.recycler_medical_item, parent, false)

        return VH(itemView)

    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: VH, position: Int) {

        holder.binding.maker.text = "업체명 : " + items[position].entpName
        holder.binding.name.text = "제품명 : " + items[position].itemName


        holder.itemView.setOnClickListener {

            val intent = Intent(context, MedicalMoreActivity::class.java)

            intent.putExtra("entpName", items[position].entpName)
            intent.putExtra("itemName", items[position].itemName)
            intent.putExtra("atpnQesitm", items[position].atpnQesitm)
            intent.putExtra("efcyQesitm", items[position].efcyQesitm)
            intent.putExtra("itemImage", items[position].itemImage)
            intent.putExtra("seQesitm", items[position].seQesitm)
            intent.putExtra("useMethodQesitm", items[position].useMethodQesitm)

            context.startActivity(intent)

        }

    }

    override fun getItemCount(): Int = items.size
}