package com.kkt1019.hospitalinmyhand.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.kkt1019.hospitalinmyhand.ItemVO
import com.kkt1019.hospitalinmyhand.databinding.FragmentEmergencyBottomsheetBinding
import com.kkt1019.hospitalinmyhand.viewmodel.SharedViewModel
import net.daum.mf.map.api.MapPOIItem
import net.daum.mf.map.api.MapPoint
import net.daum.mf.map.api.MapView
import kotlin.properties.Delegates

class EmergencyBottomSheetFragment : BottomSheetDialogFragment(), OnMapReadyCallback {

    val binding: FragmentEmergencyBottomsheetBinding by lazy { FragmentEmergencyBottomsheetBinding.inflate(layoutInflater) }

    private val sharedViewModel: SharedViewModel by activityViewModels()

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

        sharedViewModel.emergencyItem.observe(/* owner = */ viewLifecycleOwner) {
            binding.title.text = it.dutyName
            binding.address.text = it.dutyAddr
            binding.tell1.text = it.dutyTel1
            binding.tell2.text = it.dutyTel3
            Xpos = it.wgs84Lat.toDouble()
            Ypos = it.wgs84Lon.toDouble()
            name = it.dutyName
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