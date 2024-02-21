package com.kkt1019.hospitalinmyhand.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.kkt1019.hospitalinmyhand.data.PharmacyItem
import com.kkt1019.hospitalinmyhand.R
import com.kkt1019.hospitalinmyhand.data.ShareData
import com.kkt1019.hospitalinmyhand.fragment.PharmacyBottomSheet
import com.kkt1019.hospitalinmyhand.util.DistanceManager
import com.kkt1019.hospitalinmyhand.viewmodel.SharedViewModel

class PharmacyAdapter(
    val context: Context,
    var page3Items: MutableList<PharmacyItem>,
    private val fragmentManager: FragmentManager,
    private val sharedViewModel: SharedViewModel
): RecyclerView.Adapter<PharmacyAdapter.ViewHolder>() {

    fun updateData(newItems: List<PharmacyItem>) {
        page3Items.clear()
        page3Items.addAll(newItems)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val inflater:LayoutInflater = LayoutInflater.from(context)
        val itemView = inflater.inflate(R.layout.recycler_pharmacy_item, parent, false)

        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = page3Items[position]
        holder.bind(item)

        holder.itemView.setOnClickListener {

            val bottomSheetDialogFragment = PharmacyBottomSheet()
            sharedViewModel.selectPharmacyItem(item)
            bottomSheetDialogFragment.show(fragmentManager, bottomSheetDialogFragment.tag)

        }
    }

    override fun getItemCount(): Int = page3Items.size

    class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){

        val tvTitle : TextView by lazy { itemView.findViewById(R.id.pg3_title) }
        val tvAddr : TextView by lazy { itemView.findViewById(R.id.pg3_address) }
        val tvTell : TextView by lazy { itemView.findViewById(R.id.pg3_tell) }
        val tvLocation : TextView by lazy { itemView.findViewById(R.id.pg3_tv_location) }

        fun bind(item: PharmacyItem) {
            tvTitle.text ="약국 이름 : " + item.yadmNm
            tvAddr.text ="주소 : " + item.addr
            tvTell.text ="전화번호 : " + item.telno
            item.location = DistanceManager.getDistance(ShareData.lat, ShareData.lng, item.yPos.toDouble(), item.xPos.toDouble()).toString()
            tvLocation.text = item.location
        }

    }
}