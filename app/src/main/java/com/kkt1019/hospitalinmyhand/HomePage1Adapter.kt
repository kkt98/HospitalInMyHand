package com.kkt1019.hospitalinmyhand

import android.annotation.SuppressLint
import android.content.Context
import android.location.LocationManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView

class HomePage1Adapter(val context: Context, private val page1Items: MutableList<HomePage1Item>, private val fragmentManager : FragmentManager) : RecyclerView.Adapter<HomePage1Adapter.VH>(){

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
        holder.tvTimeS.text = "운영시간 : " + item.dutyTime1s + " ~ "
        holder.tvTell.text ="전화번호 : " + item.dutyTell
        holder.tvTimeC.text = item.dutyTime1c
        holder.location.text = HomePage1Fragment.DistanceManager.getDistance(G.Xpos * 1000, G.Ypos* 1000, item.wgs84Lat.toDouble()* 1000, item.wgs84Lon.toDouble()* 1000).toString()

        holder.itemView.setOnClickListener {

            val bottomSheetDialogFragment = HomePage1BottomSheet()
            bottomSheetDialogFragment.detail(item.dutyName, item.dutyAddr, item.dutyTell, item.dutyTime1s, item.dutyTime1c, item.wgs84Lat.toDouble(), item.wgs84Lon.toDouble(), item.dgidIdName
                                             ,item.dutyTime6c, item.dutyTime6s)
            bottomSheetDialogFragment.show(fragmentManager, bottomSheetDialogFragment.tag)

            G.uniqueid = item.hpid

        }

    }

    override fun getItemCount(): Int = page1Items.size
}