package com.kkt1019.hospitalinmyhand.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.ToggleButton
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.kkt1019.hospitalinmyhand.G
import com.kkt1019.hospitalinmyhand.fragment.HospitalBottomSheetFragment
import com.kkt1019.hospitalinmyhand.data.HospitalItem
import com.kkt1019.hospitalinmyhand.R
import com.kkt1019.hospitalinmyhand.data.ShareData
import com.kkt1019.hospitalinmyhand.roomdatabase.hospital.HospitalDataBase
import com.kkt1019.hospitalinmyhand.roomdatabase.hospital.HospitalEntity
import com.kkt1019.hospitalinmyhand.util.DistanceManager
import com.kkt1019.hospitalinmyhand.viewmodel.SharedViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HospitalFragmentAdapter(
    val context: Context,
    var page1Items: MutableList<HospitalItem>,
    private val fragmentManager: FragmentManager,
    private val sharedViewModel: SharedViewModel
) : RecyclerView.Adapter<HospitalFragmentAdapter.ViewHolder>(){

    fun updateData(newItems: List<HospitalItem>) {
        page1Items.clear()
        page1Items.addAll(newItems)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater: LayoutInflater = LayoutInflater.from(context)
        val itemView = inflater.inflate(R.layout.recycler_hospital_item, parent,false)

        return ViewHolder(itemView)
    }

    override fun getItemCount(): Int = page1Items.size

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = page1Items[position]
        holder.bind(item)

        holder.itemView.setOnClickListener {

            sharedViewModel.selectHospitalItem(item)
            val bottomSheetDialogFragment = HospitalBottomSheetFragment()
            bottomSheetDialogFragment.show(fragmentManager, bottomSheetDialogFragment.tag)

        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

        val tvTitle : TextView by lazy { itemView.findViewById(R.id.title) }
        val tvAddress : TextView by lazy { itemView.findViewById(R.id.address) }
        val tvTell : TextView by lazy { itemView.findViewById(R.id.tell) }
        val location : TextView by lazy { itemView.findViewById(R.id.tv_location) }
        fun bind(item: HospitalItem) {
            tvTitle.text = "병원이름 : " + item.dutyName
            tvAddress.text = "병원주소 : " + item.dutyAddr
            tvTell.text ="전화번호 : " + item.dutyTell
            G.location = DistanceManager.getDistance((ShareData.lat) , ShareData.lng, item.wgs84Lat!!.toDouble(), item.wgs84Lon!!.toDouble()).toString()
            location.text = G.location + "km"
            item.location = G.location.toString()
        }
    }

}