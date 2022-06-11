package com.kkt1019.hospitalinmyhand

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView

class HomePage1Adapter(val context: Context,  var page1Items: MutableList<HomePage1Item>, private val fragmentManager : FragmentManager) : RecyclerView.Adapter<HomePage1Adapter.VH>(){

    inner class VH(itemView: View) : RecyclerView.ViewHolder(itemView){

        val tvTitle : TextView by lazy { itemView.findViewById(R.id.title) }
        val tvAddress : TextView by lazy { itemView.findViewById(R.id.address) }
        val tvTimeS : TextView by lazy { itemView.findViewById(R.id.timeS) }
        val tvTell : TextView by lazy { itemView.findViewById(R.id.tell) }
        val tvTimeC : TextView by lazy { itemView.findViewById(R.id.timeC) }
        val location : TextView by lazy { itemView.findViewById(R.id.tv_location) }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val inflater: LayoutInflater = LayoutInflater.from(context)
        val itemView = inflater.inflate(R.layout.recycler_homepage1_item, parent,false)

        return VH(itemView)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: VH, position: Int) {
        val item = page1Items[position]

        holder.tvTitle.text = "병원이름 : " + item.dutyName
        holder.tvAddress.text = "병원주소 : " + item.dutyAddr
        holder.tvTell.text ="전화번호 : " + item.dutyTell
        holder.location.text = G.location

        item.location = G.location.toString()

//        G.location = HomePage1Fragment.DistanceManager.getDistance(G.Ypos.toDouble() , G.Xpos.toDouble(), item.wgs84Lon.toDouble(), item.wgs84Lat.toDouble()).toString()

            G.location = HomePage1Fragment.Distance.distance(G.Xpos.toDouble(), G.Ypos.toDouble(), item.wgs84Lat.toDouble(), item.wgs84Lon.toDouble())
                .toString()

        holder.itemView.setOnClickListener {

            val bottomSheetDialogFragment = HomePage1BottomSheet()
            bottomSheetDialogFragment.detail(item.dutyName, item.dutyAddr, item.dutyTell, item.dutyTime1s, item.dutyTime1c, item.wgs84Lat.toDouble(), item.wgs84Lon.toDouble(), item.dgidIdName
                                             ,item.dutyTime6c, item.dutyTime6s, item.dutyTime2s, item.dutyTime2c, item.dutyTime3s, item.dutyTime3c, item.dutyTime4s, item.dutyTime4c,
                                                item.dutyTime5s, item.dutyTime5c )
            bottomSheetDialogFragment.show(fragmentManager, bottomSheetDialogFragment.tag)

            G.uniqueid = item.hpid

        }

    }

    override fun getItemCount(): Int = page1Items.size
}