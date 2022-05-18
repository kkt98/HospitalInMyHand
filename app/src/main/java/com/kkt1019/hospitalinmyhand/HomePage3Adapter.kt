package com.kkt1019.hospitalinmyhand

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView

class HomePage3Adapter constructor(val context:Context, var page3Items: MutableList<HomePage3Item>, private val fragmentManager : FragmentManager): RecyclerView.Adapter<HomePage3Adapter.VH>() {

    inner class VH(itemView: View):RecyclerView.ViewHolder(itemView){

        val tvTitle : TextView by lazy { itemView.findViewById(R.id.pg3_title) }
        val tvAddr : TextView by lazy { itemView.findViewById(R.id.pg3_address) }
        val tvTell : TextView by lazy { itemView.findViewById(R.id.pg3_tell) }
        val tvLocation : TextView by lazy { itemView.findViewById(R.id.pg3_tv_location) }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {

        val inflater:LayoutInflater = LayoutInflater.from(context)
        val itemView = inflater.inflate(R.layout.recycler_homepage3_item, parent, false)

        return VH(itemView)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        val item = page3Items.get(position)

        holder.tvTitle.text ="약국 이름 : " + item.yadmNm
        holder.tvAddr.text ="주소 : " + item.addr
        holder.tvTell.text ="전화번호 : " + item.telno
        holder.tvLocation.text = G.location

        item.location = G.location.toString()

        G.location = PharmacyActivity.DistanceManager.getDistance(G.Xpos , G.Ypos, item.xPos.toDouble(), item.yPos.toDouble()).toString()

        holder.itemView.setOnClickListener {

            val bottomSheetDialogFragment = HomePage3BottomSheet()
            bottomSheetDialogFragment.detail(item.yadmNm, item.addr, item.telno, item.xPos.toDouble(), item.yPos.toDouble())
            bottomSheetDialogFragment.show(fragmentManager, bottomSheetDialogFragment.tag)

            G.uniqueid = item.ykiho

            Toast.makeText(context, G.uniqueid, Toast.LENGTH_SHORT).show()

        }

    }

    override fun getItemCount(): Int = page3Items.size
}