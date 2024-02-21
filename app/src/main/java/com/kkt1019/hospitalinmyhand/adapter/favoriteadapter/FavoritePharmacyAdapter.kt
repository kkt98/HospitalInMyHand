package com.kkt1019.hospitalinmyhand.adapter.favoriteadapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.kkt1019.hospitalinmyhand.R
import com.kkt1019.hospitalinmyhand.data.EmergencyItem
import com.kkt1019.hospitalinmyhand.data.PharmacyItem
import com.kkt1019.hospitalinmyhand.data.ShareData
import com.kkt1019.hospitalinmyhand.fragment.HospitalBottomSheetFragment
import com.kkt1019.hospitalinmyhand.roomdatabase.hospital.HospitalEntity
import com.kkt1019.hospitalinmyhand.roomdatabase.pharmacy.PharmacyEntity
import com.kkt1019.hospitalinmyhand.util.DistanceManager
import com.kkt1019.hospitalinmyhand.viewmodel.SharedViewModel

class FavoritePharmacyAdapter(
    val requireContext: Context,
    private var pharmacy: List<PharmacyEntity>,
    private val sharedViewModel: SharedViewModel,
    private val fragmentManager: FragmentManager
) : RecyclerView.Adapter<FavoritePharmacyAdapter.ViewHolder>() {

    fun updateData(newItems: List<PharmacyEntity>) {
        pharmacy = newItems
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recycler_pharmacy_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = pharmacy[position]
        holder.bind(item)

        holder.itemView.setOnClickListener {

            val pharmacyItem = PharmacyItem(
                yadmNm = item.yadmNm, addr = item.addr, telno = item.telno, xPos = item.xPos, yPos = item.yPos,
                ykiho = item.ykiho, location = item.location) // 마커 아이콘 수정

            sharedViewModel.selectPharmacyItem(pharmacyItem)
            val bottomSheetDialogFragment = HospitalBottomSheetFragment()
            bottomSheetDialogFragment.show(fragmentManager, bottomSheetDialogFragment.tag)
        }
    }

    override fun getItemCount(): Int = pharmacy.size

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val tvTitle : TextView by lazy { itemView.findViewById(R.id.pg3_title) }
        val tvAddr : TextView by lazy { itemView.findViewById(R.id.pg3_address) }
        val tvTell : TextView by lazy { itemView.findViewById(R.id.pg3_tell) }
        val tvLocation : TextView by lazy { itemView.findViewById(R.id.pg3_tv_location) }


        fun bind(item: PharmacyEntity) {
            tvTitle.text ="약국 이름 : " + item.yadmNm
            tvAddr.text ="주소 : " + item.addr
            tvTell.text ="전화번호 : " + item.telno
            tvLocation.text = item.location
        }
    }
}