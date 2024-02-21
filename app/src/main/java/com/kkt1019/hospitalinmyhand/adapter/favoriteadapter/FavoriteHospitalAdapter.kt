package com.kkt1019.hospitalinmyhand.adapter.favoriteadapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.ToggleButton
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.kkt1019.hospitalinmyhand.R
import com.kkt1019.hospitalinmyhand.data.HospitalItem
import com.kkt1019.hospitalinmyhand.fragment.HospitalBottomSheetFragment
import com.kkt1019.hospitalinmyhand.roomdatabase.hospital.HospitalDataBase
import com.kkt1019.hospitalinmyhand.roomdatabase.hospital.HospitalEntity
import com.kkt1019.hospitalinmyhand.viewmodel.SharedViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FavoriteHospitalAdapter(
    val context: Context,
    private var hospitals: List<HospitalEntity>,
    private var sharedViewModel: SharedViewModel,
    private val fragmentManager: FragmentManager
): RecyclerView.Adapter<FavoriteHospitalAdapter.ViewHolder>() {

    fun updateData(newItems: List<HospitalEntity>) {
        hospitals = newItems
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recycler_hospital_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = hospitals[position]
        holder.bind(item)

        holder.itemView.setOnClickListener {

            val hospitalItem = HospitalItem(
                dutyAddr = item.dutyAddr, dutyName = item.dutyName, dutyTell = item.dutyTell, dutyTime1s = item.dutyTime1s,
                dutyTime2s = item.dutyTime2s, dutyTime2c = item.dutyTime2c, dutyTime3s = item.dutyTime3s, dutyTime3c = item.dutyTime3c,
                dutyTime4s = item.dutyTime4s, dutyTime4c = item.dutyTime4c, dutyTime5s = item.dutyTime5s, dutyTime5c = item.dutyTime5c,
                dutyTime6s = item.dutyTime6s, dutyTime6c = item.dutyTime6c, wgs84Lat = item.wgs84Lat, wgs84Lon = item.wgs84Lon,
                hpid = item.hpid, dgidIdName = item.dgidIdName, location = item.location) // 마커 아이콘 수정

            sharedViewModel.selectHospitalItem(hospitalItem)
            val bottomSheetDialogFragment = HospitalBottomSheetFragment()
            bottomSheetDialogFragment.show(fragmentManager, bottomSheetDialogFragment.tag)
        }

//        CoroutineScope(Dispatchers.IO).launch {
//            val isSaved = HospitalDataBase.getDatabase(context).hospitalDao().exists(item.hpid!!)
//            withContext(Dispatchers.Main) {
//                // UI 스레드에서 토글 버튼 상태 업데이트
//                holder.toggle.isChecked = isSaved
//            }
//        }
    }

    override fun getItemCount(): Int = hospitals.size

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvTitle: TextView = itemView.findViewById(R.id.title)
        private val tvAddress: TextView = itemView.findViewById(R.id.address)
        private val tvTell: TextView = itemView.findViewById(R.id.tell)
        private val location: TextView = itemView.findViewById(R.id.tv_location)

        fun bind(hospital: HospitalEntity) {
            tvTitle.text = hospital.dutyName
            tvAddress.text = hospital.dutyAddr
            tvTell.text = hospital.dutyTell
            location.text = hospital.location + "km"
        }
    }
}