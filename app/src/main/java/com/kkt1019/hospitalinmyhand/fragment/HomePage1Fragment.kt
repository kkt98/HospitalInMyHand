package com.kkt1019.hospitalinmyhand.fragment

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.kkt1019.hospitalinmyhand.data.HomePage1Item
import com.kkt1019.hospitalinmyhand.R
import com.kkt1019.hospitalinmyhand.adapter.HomePage1Adapter
import com.kkt1019.hospitalinmyhand.databinding.FragmentHomePage1Binding
import com.kkt1019.hospitalinmyhand.viewmodel.HospitalViewModel
import kotlin.math.*

class HomePage1Fragment : Fragment() {

    private var items = mutableListOf<HomePage1Item>()
    private var allItems = mutableListOf<HomePage1Item>()
    private var _binding: FragmentHomePage1Binding? = null
    private val binding get() = _binding!!

    private val networkViewModel: HospitalViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomePage1Binding.inflate(inflater, container, false)
        binding.recycler.adapter = HomePage1Adapter(requireContext(), items, childFragmentManager)

        binding.btn.setOnClickListener { showDialog() }

        networkViewModel.hospitalData.observe(viewLifecycleOwner, Observer {
            allItems.addAll(it)
            (binding.recycler.adapter as? HomePage1Adapter)?.updateData(it)
        })

        networkViewModel.fetchDataFromNetwork()

        return binding.root
    }

    private fun showDialog() {
        val mDialogView = LayoutInflater.from(requireContext()).inflate(R.layout.dialog, null)
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

        // Initialize spinner2 and spinner3 in updateNeighborhoodSpinner and updateHospitalTypeSpinner respectively
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
                // 여기서 updateHospitalTypeSpinner 함수를 호출합니다.
                // 선택된 동네 이름을 전달하거나, 동네 선택에 따른 병원 종류 데이터를 설정하는 데 사용할 수 있습니다.
                // 이 예시에서는 동네 이름을 사용하지 않고 병원 종류 스피너를 직접 업데이트합니다.
                val spinner3 = view?.rootView?.findViewById<Spinner>(R.id.spinner3)
                spinner3?.let {
                    updateHospitalTypeSpinner(it)
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }
    }

    private fun updateHospitalTypeSpinner(spinner3: Spinner) {
        val hospitalTypeAdapter = ArrayAdapter.createFromResource(requireContext(), R.array.medical_id, android.R.layout.simple_spinner_dropdown_item)
        spinner3.adapter = hospitalTypeAdapter
    }

    private fun filterAndDisplayItems(mDialogView: View) {
        val spinner = mDialogView.findViewById<Spinner>(R.id.spinner)
        val spinner2 = mDialogView.findViewById<Spinner>(R.id.spinner2)
        val spinner3 = mDialogView.findViewById<Spinner>(R.id.spinner3)

        val selectedCity = spinner.selectedItem.toString()
        val selectedNeighborhood = spinner2.selectedItem.toString()
        val selectedHospitalType = spinner3.selectedItem.toString()

        items = allItems.filter { item ->
            item.dutyAddr.contains(selectedCity) &&
                    item.dutyAddr.contains(selectedNeighborhood) &&
                    item.dgidIdName.contains(selectedHospitalType)
        }.toMutableList()

        (binding.recycler.adapter as? HomePage1Adapter)?.let { adapter ->
            adapter.updateData(items)
            adapter.notifyDataSetChanged()
        }
    }


    object DistanceManager {

        private const val R = 6372.8

        /**
         * 두 좌표의 거리를 계산한다.
         *
         * @param lat1 위도1
         * @param lon1 경도1
         * @param lat2 위도2
         * @param lon2 경도2
         * @return 두 좌표의 거리(m)
         */
        fun getDistance(lat1: Double, lon1: Double, lat2: Double, lon2: Double): Double {
            val dLat = Math.toRadians(lat1 - lat2)
            val dLon = Math.toRadians(lon1 - lon2)
            val a = sin(dLat / 2).pow(2.0) + sin(dLon / 2).pow(2.0) * cos(Math.toRadians(lat1)) * cos(Math.toRadians(lat2))
            val c = 2 * asin(sqrt(a))
            return (round((R * c)*100) / 100)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

