package com.kkt1019.hospitalinmyhand.fragment

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.CheckBox
import android.widget.Spinner
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.kkt1019.hospitalinmyhand.data.HospitalItem
import com.kkt1019.hospitalinmyhand.R
import com.kkt1019.hospitalinmyhand.adapter.HospitalFragmentAdapter
import com.kkt1019.hospitalinmyhand.data.ShareData
import com.kkt1019.hospitalinmyhand.databinding.FragmentHospitalBinding
import com.kkt1019.hospitalinmyhand.viewmodel.HospitalViewModel
import com.kkt1019.hospitalinmyhand.viewmodel.SharedViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

@AndroidEntryPoint
class HospitalFragment : Fragment() {

    private var items = mutableListOf<HospitalItem>()
    private var allItems = mutableListOf<HospitalItem>()
    private var _binding: FragmentHospitalBinding? = null
    private val binding get() = _binding!!

    private val hospitalViewModel: HospitalViewModel by viewModels()
    private val sharedViewModel: SharedViewModel by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHospitalBinding.inflate(inflater, container, false)
        binding.recycler.adapter = HospitalFragmentAdapter(requireContext(), items, childFragmentManager, sharedViewModel)

        binding.btn.setOnClickListener { showDialog() }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.sflSample.startShimmer()
        binding.sflSample.visibility = View.VISIBLE

        hospitalViewModel.fetchDataFromNetwork()
        hospitalViewModel.hospitalData.observe(viewLifecycleOwner, Observer {
            allItems.addAll(it)
            (binding.recycler.adapter as? HospitalFragmentAdapter)?.updateData(it)

            binding.sflSample.stopShimmer()
            binding.sflSample.visibility = View.GONE

        })

        hospitalViewModel.filteredHospitalData.observe(viewLifecycleOwner) { data ->

            // 데이터가 변경될 때 UI 업데이트
            (binding.recycler.adapter as? HospitalFragmentAdapter)?.updateData(data)

            binding.sflSample.stopShimmer()
            binding.sflSample.visibility = View.GONE
        }
    }
    private fun showDialog() {
        val mDialogView = LayoutInflater.from(requireContext()).inflate(R.layout.dialog, null)
        val checkBox = mDialogView.findViewById<CheckBox>(R.id.check_my)
        val spinner = mDialogView.findViewById<Spinner>(R.id.spinner)
        val spinner2 = mDialogView.findViewById<Spinner>(R.id.spinner2)

        // 체크박스 상태에 따라 스피너 가시성 조정
        checkBox.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                spinner.visibility = View.GONE
                spinner2.visibility = View.GONE
            } else {
                spinner.visibility = View.VISIBLE
                spinner2.visibility = View.VISIBLE
            }
        }

        val dialog = AlertDialog.Builder(requireContext())
            .setView(mDialogView)
            .setPositiveButton("확인", null)
            .setNegativeButton("취소") { dialogInterface, _ -> dialogInterface.cancel() }
            .create()

        dialog.show()

        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener {

            items.clear()
            (binding.recycler.adapter as? HospitalFragmentAdapter)?.notifyDataSetChanged()
            binding.sflSample.startShimmer()
            binding.sflSample.visibility = View.VISIBLE

            if (checkBox.isChecked) {
                // 체크박스가 선택된 경우, 사용자 위치 기반으로 병원 목록 정렬 및 업데이트
                nearByMyLocation(mDialogView)
            } else {
                // 체크박스가 해제된 경우, 기존 필터링 로직 실행
                filterAndDisplayItems(mDialogView)
            }
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
            else -> R.array.choice
        }
        val neighborhoodAdapter = ArrayAdapter.createFromResource(requireContext(), neighborhoodArrayId, android.R.layout.simple_spinner_dropdown_item)
        spinner2.adapter = neighborhoodAdapter

        spinner2.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                val spinner3 = view?.rootView?.findViewById<Spinner>(R.id.spinner3)

                val hospitalTypeAdapter = ArrayAdapter.createFromResource(requireContext(), R.array.medical_id, android.R.layout.simple_spinner_dropdown_item)
                spinner3?.adapter = hospitalTypeAdapter
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }
    }

    private fun filterAndDisplayItems(mDialogView: View) {
        val spinner = mDialogView.findViewById<Spinner>(R.id.spinner)
        val spinner2 = mDialogView.findViewById<Spinner>(R.id.spinner2)
        val spinner3 = mDialogView.findViewById<Spinner>(R.id.spinner3)

        val selectedCity = if (spinner.selectedItemPosition > 0) spinner.selectedItem.toString() else null
        val selectedNeighborhood = if (spinner2.selectedItemPosition > 0) spinner2.selectedItem.toString() else null
        val selectedHospitalType = if (spinner3.selectedItemPosition > 0) spinner3.selectedItem.toString() else null

        hospitalViewModel.fetchDataAndFilter((selectedCity!!), (selectedNeighborhood!!), selectedHospitalType ?: "내과")
    }

    private fun nearByMyLocation(mDialogView: View) {

        val spinner3 = mDialogView.findViewById<Spinner>(R.id.spinner3)
        val selectedHospitalType = if (spinner3.selectedItemPosition > 0) spinner3.selectedItem.toString() else null

        hospitalViewModel.sortByUserLocation(ShareData.lat, ShareData.lng, selectedHospitalType)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

