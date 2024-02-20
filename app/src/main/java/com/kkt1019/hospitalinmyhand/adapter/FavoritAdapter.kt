package com.kkt1019.hospitalinmyhand.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.ToggleButton
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.kkt1019.hospitalinmyhand.G
import com.kkt1019.hospitalinmyhand.R
import com.kkt1019.hospitalinmyhand.data.EmergencyItem
import com.kkt1019.hospitalinmyhand.data.HospitalItem
import com.kkt1019.hospitalinmyhand.data.PharmacyItem
import com.kkt1019.hospitalinmyhand.fragment.EmergencyBottomSheetFragment
import com.kkt1019.hospitalinmyhand.fragment.HospitalBottomSheetFragment
import com.kkt1019.hospitalinmyhand.fragment.PharmacyBottomSheet
import com.kkt1019.hospitalinmyhand.viewmodel.SharedViewModel

class FavoritAdapter(
    private val context: Context,
    private val items: MutableList<Any>,
    private val sharedViewModel: SharedViewModel,
    private val fragmentManager: FragmentManager
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    companion object {
        private const val TYPE_EMERGENCY = 0
        private const val TYPE_HOSPITAL = 1
        private const val TYPE_PHARMACY = 2
    }

    fun updateData(newItems: List<Any>) {
        items.clear()
        items.addAll(newItems)
        notifyDataSetChanged()
    }

    override fun getItemViewType(position: Int): Int {
        return when (items[position]) {
            is EmergencyItem -> TYPE_EMERGENCY
            is HospitalItem -> TYPE_HOSPITAL
            is PharmacyItem -> TYPE_PHARMACY
            else -> throw IllegalArgumentException("Invalid type of data $position")
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(context)
        return when (viewType) {
            TYPE_EMERGENCY -> EmergencyViewHolder(inflater.inflate(R.layout.recycler_emergency_item, parent, false))
            TYPE_HOSPITAL -> HospitalViewHolder(inflater.inflate(R.layout.recycler_hospital_item, parent, false))
            TYPE_PHARMACY -> PharmacyViewHolder(inflater.inflate(R.layout.recycler_pharmacy_item, parent, false))
            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = items[position]
        when (holder) {
            is EmergencyViewHolder -> holder.bind(item as EmergencyItem, sharedViewModel, fragmentManager)
            is HospitalViewHolder -> holder.bind(item as HospitalItem, sharedViewModel, fragmentManager)
            is PharmacyViewHolder -> holder.bind(item as PharmacyItem, sharedViewModel, fragmentManager)
        }
    }

    override fun getItemCount(): Int = items.size

    class EmergencyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val tvTitle : TextView by lazy { itemView.findViewById(R.id.pg2_title) }
        val tvAddr : TextView by lazy { itemView.findViewById(R.id.pg2_address) }
        val tvTell : TextView by lazy { itemView.findViewById(R.id.pg2_tell) }
        val tvTell2 : TextView by lazy { itemView.findViewById(R.id.pg2_tell2) }
        val tvLocation : TextView by lazy { itemView.findViewById(R.id.pg2_tv_location) }

        fun bind(
            item: EmergencyItem,
            sharedViewModel: SharedViewModel,
            fragmentManager: FragmentManager
        ) {
            tvTitle.text = "응급실이름 : " + item.dutyName
            tvAddr.text = "주소 : " + item.dutyAddr
            tvTell.text = "대표 전화 : " + item.dutyTel1
            tvTell2.text = "응급실 전화 : " + item.dutyTel3
            tvLocation.text = item.location + "km"

            itemView.setOnClickListener {
                sharedViewModel.selectEmergencyItem(item)
                val bottomSheetDialogFragment = EmergencyBottomSheetFragment()
                bottomSheetDialogFragment.show(fragmentManager, bottomSheetDialogFragment.tag)
            }
        }
    }

    class HospitalViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val tvTitle : TextView by lazy { itemView.findViewById(R.id.title) }
        val tvAddress : TextView by lazy { itemView.findViewById(R.id.address) }
        val tvTell : TextView by lazy { itemView.findViewById(R.id.tell) }
        val location : TextView by lazy { itemView.findViewById(R.id.tv_location) }
        fun bind(
            item: HospitalItem,
            sharedViewModel: SharedViewModel,
            fragmentManager: FragmentManager
        ) {
            tvTitle.text = "병원이름 : " + item.dutyName
            tvAddress.text = "병원주소 : " + item.dutyAddr
            tvTell.text ="전화번호 : " + item.dutyTell
            location.text = G.location + "km"

            itemView.setOnClickListener {
                sharedViewModel.selectHospitalItem(item)
                val bottomSheetDialogFragment = HospitalBottomSheetFragment()
                bottomSheetDialogFragment.show(fragmentManager, bottomSheetDialogFragment.tag)

            }
        }
    }

    class PharmacyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val tvTitle : TextView by lazy { itemView.findViewById(R.id.pg3_title) }
        val tvAddr : TextView by lazy { itemView.findViewById(R.id.pg3_address) }
        val tvTell : TextView by lazy { itemView.findViewById(R.id.pg3_tell) }
        val tvLocation : TextView by lazy { itemView.findViewById(R.id.pg3_tv_location) }
        fun bind(
            item: PharmacyItem,
            sharedViewModel: SharedViewModel,
            fragmentManager: FragmentManager
        ) {
            tvTitle.text ="약국 이름 : " + item.yadmNm
            tvAddr.text ="주소 : " + item.addr
            tvTell.text ="전화번호 : " + item.telno
            tvLocation.text = item.location

            itemView.setOnClickListener {
                val bottomSheetDialogFragment = PharmacyBottomSheet()
                sharedViewModel.selectPharmacyItem(item)
                bottomSheetDialogFragment.show(fragmentManager, bottomSheetDialogFragment.tag)
            }
        }
    }}