package com.kkt1019.hospitalinmyhand.adapter.favoriteadapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.kkt1019.hospitalinmyhand.G
import com.kkt1019.hospitalinmyhand.R
import com.kkt1019.hospitalinmyhand.data.EmergencyItem
import com.kkt1019.hospitalinmyhand.data.HospitalItem
import com.kkt1019.hospitalinmyhand.data.ShareData
import com.kkt1019.hospitalinmyhand.fragment.EmergencyBottomSheetFragment
import com.kkt1019.hospitalinmyhand.fragment.HospitalBottomSheetFragment
import com.kkt1019.hospitalinmyhand.fragment.PharmacyBottomSheet
import com.kkt1019.hospitalinmyhand.roomdatabase.emergency.EmergencyEntity
import com.kkt1019.hospitalinmyhand.util.DistanceManager
import com.kkt1019.hospitalinmyhand.viewmodel.SharedViewModel

class FavoriteEmergencyAdapter(
    val context: Context,
    private var emergency: List<EmergencyEntity>,
    private val sharedViewModel: SharedViewModel,
    private val fragmentManager: FragmentManager,
) : RecyclerView.Adapter<FavoriteEmergencyAdapter.ViewHolder>() {

    fun updateData(newItems: List<EmergencyEntity>) {
        emergency = newItems
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recycler_emergency_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = emergency.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = emergency[position]
        holder.bind(item)

        holder.itemView.setOnClickListener {

            val emergencyItem = EmergencyItem(
                dutyAddr = item.dutyAddr, dutyName = item.dutyName, dutyTel1 = item.dutyTel1, dutyTel3 = item.dutyTel3, wgs84Lat = item.wgs84Lat,
                wgs84Lon = item.wgs84Lon, hpid = item.hpid, location = item.location) // 마커 아이콘 수정

            sharedViewModel.selectEmergencyItem(emergencyItem)
            val bottomSheetDialogFragment = PharmacyBottomSheet()
            bottomSheetDialogFragment.show(fragmentManager, bottomSheetDialogFragment.tag)
        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvTitle : TextView by lazy { itemView.findViewById(R.id.pg2_title) }
        val tvAddr : TextView by lazy { itemView.findViewById(R.id.pg2_address) }
        val tvTell : TextView by lazy { itemView.findViewById(R.id.pg2_tell) }
        val tvTell2 : TextView by lazy { itemView.findViewById(R.id.pg2_tell2) }
        val tvLocation : TextView by lazy { itemView.findViewById(R.id.pg2_tv_location) }

        fun bind(item: EmergencyEntity) {
            tvTitle.text = "응급실이름 : " + item.dutyName
            tvAddr.text = "주소 : " + item.dutyAddr
            tvTell.text = "대표 전화 : " + item.dutyTel1
            tvTell2.text = "응급실 전화 : " + item.dutyTel3
            tvLocation.text = item.location + "km"
        }
    }
}