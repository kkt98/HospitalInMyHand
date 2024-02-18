package com.kkt1019.hospitalinmyhand.fragment

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.kkt1019.hospitalinmyhand.adapter.EmergencyFragmentAdapter
import com.kkt1019.hospitalinmyhand.data.HomePage2Item
import com.kkt1019.hospitalinmyhand.R
import com.kkt1019.hospitalinmyhand.databinding.FragmentEmergencyBinding
import com.kkt1019.hospitalinmyhand.viewmodel.EmergencyViewModel
import com.kkt1019.hospitalinmyhand.viewmodel.SharedViewModel

class EmergencyFragment:Fragment() {

    var items = mutableListOf<HomePage2Item>()
    var allItems = mutableListOf<HomePage2Item>()

    private val emergencyViewModel : EmergencyViewModel by viewModels()
    private val sharedViewModel: SharedViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding.recycler.adapter = EmergencyFragmentAdapter(activity as Context, items, childFragmentManager, sharedViewModel)

        binding.btn.setOnClickListener { showDialog() }

        return binding.root
    }

    override fun onResume() {
        super.onResume()

//        Mylocation()

        binding.sflSample.startShimmer()
        binding.sflSample.visibility = View.VISIBLE

        emergencyViewModel.fetchDataFromNetwork()

        emergencyViewModel.emergencyItem.observe(requireActivity(), Observer {
            allItems.addAll(it)

            (binding.recycler.adapter as? EmergencyFragmentAdapter)?.updateData(it)

            binding.sflSample.stopShimmer()
            binding.sflSample.visibility = View.GONE

        })

    }

    val binding: FragmentEmergencyBinding by lazy { FragmentEmergencyBinding.inflate(layoutInflater) }

    private fun showDialog() {
        val mDialogView = LayoutInflater.from(requireContext()).inflate(R.layout.dialog2, null)
        val dialog = AlertDialog.Builder(requireContext())
            .setView(mDialogView)
            .setPositiveButton("확인", null) // Initially, we don't handle the click here
            .setNegativeButton("취소") { dialogInterface, _ -> dialogInterface.cancel() }
            .create()

        dialog.show()

        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener {
            // '확인' 버튼 클릭 시 필터링 및 업데이트 로직
            filterAndDisplayItems(mDialogView)
            dialog.dismiss()
        }

        setupSpinners(mDialogView)
    }

    private fun setupSpinners(mDialogView: View) {
        val spinner = mDialogView.findViewById<Spinner>(R.id.spinner)
        val spinner2 = mDialogView.findViewById<Spinner>(R.id.spinner2)

        val cityAdapter = ArrayAdapter.createFromResource(requireContext(), R.array.city, android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = cityAdapter

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                val selectedCity = parent.getItemAtPosition(position).toString()
                updateNeighborhoodSpinner(selectedCity, spinner2)
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }
    }

    private fun updateNeighborhoodSpinner(city: String, spinner2: Spinner) {
        val neighborhoodArrayId = when(city) {
            "서울특별시" -> R.array.spinner_region_seoul
            "부산광역시" -> R.array.spinner_region_busan
            "대구광역시" -> R.array.spinner_region_daegu
            "인천광역시" -> R.array.spinner_region_incheon
            "광주광역시" -> R.array.spinner_region_gwangju
            "대전광역시" -> R.array.spinner_region_daejeon
            "울산광역시" -> R.array.spinner_region_ulsan
            "세종특별자치시" -> R.array.spinner_region_sejong
            "경기도" -> R.array.spinner_region_gyeonggi
            "강원도" -> R.array.spinner_region_gangwon
            "충청북도" -> R.array.spinner_region_chung_buk
            "충청남도" -> R.array.spinner_region_chung_nam
            "전라북도" -> R.array.spinner_region_gyeong_buk
            "전라남도" -> R.array.spinner_region_gyeong_nam
            "경상북도" -> R.array.spinner_region_jeon_buk
            "경상남도" -> R.array.spinner_region_jeon_nam
            "제주특별자치도" -> R.array.spinner_jeju
            else -> R.array.choice // 기본값 또는 지원되지 않는 도시
        }
        val neighborhoodAdapter = ArrayAdapter.createFromResource(requireContext(), neighborhoodArrayId, android.R.layout.simple_spinner_dropdown_item)
        spinner2.adapter = neighborhoodAdapter
    }

    private fun filterAndDisplayItems(mDialogView: View) {
        val spinner = mDialogView.findViewById<Spinner>(R.id.spinner)
        val spinner2 = mDialogView.findViewById<Spinner>(R.id.spinner2)

        val selectedCity = spinner.selectedItem.toString()
        val selectedNeighborhood = spinner2.selectedItem.toString()

        items = allItems.filter { item ->
            item.dutyAddr.contains(selectedCity) && item.dutyAddr.contains(selectedNeighborhood)
        }.toMutableList()

        (binding.recycler.adapter as? EmergencyFragmentAdapter)?.let { adapter ->
            adapter.updateData(items)
            adapter.notifyDataSetChanged()
        }
    }

}