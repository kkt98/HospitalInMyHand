package com.kkt1019.hospitalinmyhand.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.kkt1019.hospitalinmyhand.databinding.FragmentEmergencyBottomsheetBinding
import com.kkt1019.hospitalinmyhand.viewmodel.RoomViewModel
import com.kkt1019.hospitalinmyhand.viewmodel.SharedViewModel

class EmergencyBottomSheetFragment : BottomSheetDialogFragment(), OnMapReadyCallback {

    val binding: FragmentEmergencyBottomsheetBinding by lazy { FragmentEmergencyBottomsheetBinding.inflate(layoutInflater) }

    private val sharedViewModel: SharedViewModel by activityViewModels()
    private val roomViewModel: RoomViewModel by viewModels()

    var name : String = ""
    var Xpos : Double = 0.0
    var Ypos : Double = 0.0

    private lateinit var mapView: com.google.android.gms.maps.MapView
    private lateinit var googleMap: GoogleMap
    private var currentMarker: Marker? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        this.mapView = binding.mapView
        mapView.onCreate(savedInstanceState)
        mapView.getMapAsync(this)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sharedViewModel.emergencyItem.observe(/* owner = */ viewLifecycleOwner) { item ->
            binding.title.text = item.dutyName
            binding.address.text = item.dutyAddr
            binding.tell1.text = item.dutyTel1
            binding.tell2.text = item.dutyTel3
            Xpos = item.wgs84Lat.toDouble()
            Ypos = item.wgs84Lon.toDouble()
            name = item.dutyName

            binding.favorite.setOnClickListener {
                roomViewModel.insertEmergencyItem(item)
            }
        }

        roomViewModel.uiMessage.observe(viewLifecycleOwner) {
            Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
        }

    }

    override fun onMapReady(googleMap: GoogleMap) {
        this.googleMap = googleMap

        currentMarker = setupMarker()  // default 서울역
        currentMarker?.showInfoWindow()
    }

    private fun setupMarker(): Marker? {

        val positionLatLng = LatLng(Xpos,Ypos)
        val markerOption = MarkerOptions().apply {
            position(positionLatLng)
            title(name)
        }

        googleMap.mapType = GoogleMap.MAP_TYPE_NORMAL  // 지도 유형 설정
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(positionLatLng, 15f))  // 카메라 이동
        googleMap.animateCamera(CameraUpdateFactory.zoomTo(15f))  // 줌의 정도 - 1 일 경우 세계지도 수준, 숫자가 커질 수록 상세지도가 표시됨
        return googleMap.addMarker(markerOption)

    }
}