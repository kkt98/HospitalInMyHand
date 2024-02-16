package com.kkt1019.hospitalinmyhand.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.kkt1019.hospitalinmyhand.G
import com.kkt1019.hospitalinmyhand.HomePage2BottomSheet
import com.kkt1019.hospitalinmyhand.HomePage2Item
import com.kkt1019.hospitalinmyhand.R
import com.kkt1019.hospitalinmyhand.fragment.HomePage1Fragment

class HomePage2Adapter constructor(val context:Context, var page2Items:MutableList<HomePage2Item>, private val fragmentManager : FragmentManager): RecyclerView.Adapter<HomePage2Adapter.VH>() {

    inner class VH(itemView: View): RecyclerView.ViewHolder(itemView){

        val tvTitle : TextView by lazy { itemView.findViewById(R.id.pg2_title) }
        val tvAddr : TextView by lazy { itemView.findViewById(R.id.pg2_address) }
        val tvTell : TextView by lazy { itemView.findViewById(R.id.pg2_tell) }
        val tvTell2 : TextView by lazy { itemView.findViewById(R.id.pg2_tell2) }
        val tvLocation : TextView by lazy { itemView.findViewById(R.id.pg2_tv_location) }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {

        val inflater: LayoutInflater = LayoutInflater.from(context)
        val itemView = inflater.inflate(R.layout.recycler_homepage2_item, parent, false)

        return VH(itemView)

    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        val item = page2Items[position]

        holder.tvTitle.text = "응급실이름 : " + item.dutyName
        holder.tvAddr.text = "주소 : " + item.dutyAddr
        holder.tvTell.text = "대표 전화 : " + item.dutyTel1
        holder.tvTell2.text = "응급실 전화 : " + item.dutyTel3
        holder.tvLocation.text = G.location

//        item.location = G.location.toString()

        G.location = HomePage1Fragment.DistanceManager.getDistance(G.Xpos.toDouble(), G.Ypos.toDouble(), item.wgs84Lat.toDouble() ?: 37.5663, item.wgs84Lon.toDouble() ?: 126.9779)
            .toString()





        holder.itemView.setOnClickListener {

            val bottomSheetDialogFragment = HomePage2BottomSheet()
            bottomSheetDialogFragment.detail(item.dutyName, item.dutyAddr, item.dutyTel1, item.dutyTel3, item.wgs84Lat.toDouble(), item.wgs84Lon.toDouble())
            bottomSheetDialogFragment.show(fragmentManager, bottomSheetDialogFragment.tag)

            G.uniqueid = item.hpid

            Toast.makeText(context, G.uniqueid, Toast.LENGTH_SHORT).show()
        }
    }

    override fun getItemCount(): Int = page2Items.size
}