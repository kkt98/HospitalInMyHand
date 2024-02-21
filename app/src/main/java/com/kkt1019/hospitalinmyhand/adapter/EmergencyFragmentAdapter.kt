package com.kkt1019.hospitalinmyhand.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.kkt1019.hospitalinmyhand.G
import com.kkt1019.hospitalinmyhand.fragment.EmergencyBottomSheetFragment
import com.kkt1019.hospitalinmyhand.data.EmergencyItem
import com.kkt1019.hospitalinmyhand.R
import com.kkt1019.hospitalinmyhand.data.ShareData
import com.kkt1019.hospitalinmyhand.util.DistanceManager
import com.kkt1019.hospitalinmyhand.viewmodel.SharedViewModel

class EmergencyFragmentAdapter (
    val context:Context,
    var page2Items:MutableList<EmergencyItem>,
    private val fragmentManager : FragmentManager,
    private val sharedViewModel: SharedViewModel): RecyclerView.Adapter<EmergencyFragmentAdapter.ViewHolder>() {

    fun updateData(newItems: List<EmergencyItem>) {
        page2Items.clear()
        page2Items.addAll(newItems)
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val inflater: LayoutInflater = LayoutInflater.from(context)
        val itemView = inflater.inflate(R.layout.recycler_emergency_item, parent, false)

        return ViewHolder(itemView)

    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = page2Items[position]

        holder.tvTitle.text = "응급실이름 : " + item.dutyName
        holder.tvAddr.text = "주소 : " + item.dutyAddr
        holder.tvTell.text = "대표 전화 : " + item.dutyTel1
        holder.tvTell2.text = "응급실 전화 : " + item.dutyTel3
        G.location = DistanceManager.getDistance(ShareData.lat, ShareData.lng, item.wgs84Lat.toDouble(), item.wgs84Lon.toDouble()).toString()
        holder.tvLocation.text = G.location + "km"
        item.location = G.location.toString()

        holder.itemView.setOnClickListener {
            sharedViewModel.selectEmergencyItem(item)
            val bottomSheetDialogFragment = EmergencyBottomSheetFragment()
            bottomSheetDialogFragment.show(fragmentManager, bottomSheetDialogFragment.tag)
        }
    }

    override fun getItemCount(): Int = page2Items.size

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){

        val tvTitle : TextView by lazy { itemView.findViewById(R.id.pg2_title) }
        val tvAddr : TextView by lazy { itemView.findViewById(R.id.pg2_address) }
        val tvTell : TextView by lazy { itemView.findViewById(R.id.pg2_tell) }
        val tvTell2 : TextView by lazy { itemView.findViewById(R.id.pg2_tell2) }
        val tvLocation : TextView by lazy { itemView.findViewById(R.id.pg2_tv_location) }

    }
}