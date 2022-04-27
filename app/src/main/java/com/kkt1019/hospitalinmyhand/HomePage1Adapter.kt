package com.kkt1019.hospitalinmyhand

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.kkt1019.hospitalinmyhand.databinding.RecyclerHomepage1ItemBinding

class HomePage1Adapter(val context: Context, val page1Items: MutableList<HomePage1Item>,val fragmentManager : FragmentManager) : RecyclerView.Adapter<HomePage1Adapter.VH>(){


    inner class VH(itemView: View) : RecyclerView.ViewHolder(itemView){

        val tvTitle : TextView by lazy { itemView.findViewById(R.id.title) }
        val tvAddress : TextView by lazy { itemView.findViewById(R.id.address) }
        val tvTimeS : TextView by lazy { itemView.findViewById(R.id.timeS) }
        val tvTell : TextView by lazy { itemView.findViewById(R.id.tell) }
        val tvTimeC : TextView by lazy { itemView.findViewById(R.id.timeC) }

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
        holder.tvTimeS.setText(item.dutyTime1s)
        holder.tvTell.setText(item.dutyTell)
        holder.tvTimeC.setText(item.dutyTime1c)

        holder.itemView.setOnClickListener {

            val bottomSheetDialogFragment = HomePage1BottomSheet()
            bottomSheetDialogFragment.show(fragmentManager, bottomSheetDialogFragment.tag)

        }

    }

    override fun getItemCount(): Int = page1Items.size
}