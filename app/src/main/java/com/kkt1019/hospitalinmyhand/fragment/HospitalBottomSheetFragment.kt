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
import com.kkt1019.hospitalinmyhand.databinding.FragmentHospitalBottomsheetBinding
import com.kkt1019.hospitalinmyhand.viewmodel.SharedViewModel
import net.daum.mf.map.api.MapPOIItem
import net.daum.mf.map.api.MapPoint
import net.daum.mf.map.api.MapView

class HospitalBottomSheetFragment : BottomSheetDialogFragment(), OnMapReadyCallback {

    val binding: FragmentHospitalBottomsheetBinding by lazy { FragmentHospitalBottomsheetBinding.inflate(layoutInflater) }

    private val sharedViewModel: SharedViewModel by activityViewModels()

    var name : String = ""
    var  Xpos :Double = 0.0
    var Ypos :Double = 0.0

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


        sharedViewModel.hospitalItem.observe(viewLifecycleOwner) { item ->

            Log.d("Asdadad", item.dutyAddr)

            binding.title.text = item.dutyName
            binding.tell.text = item.dutyTell
            binding.address.text = item.dutyAddr
            binding.timeS.text = "월 : " + item.dutyTime1s+" ~ "
            binding.timeC.text = item.dutyTime1s
            binding.time1S.text = "화 : " + item.dutyTime2s+" ~ "
            binding.time1C.text = item.dutyTime2c
            binding.time3S.text = "수 : " + item.dutyTime3s+" ~ "
            binding.time3C.text = item.dutyTime3c
            binding.time2S.text = "목 : " + item.dutyTime4s+" ~ "
            binding.time2C.text = item.dutyTime4c
            binding.time4S.text = "금 : " + item.dutyTime5s+" ~ "
            binding.time4C.text = item.dutyTime5c
            binding.time5S.text = "토 : " + item.dutyTime6s+" ~ "
            binding.time5C.text = item.dutyTime6c

            Xpos = item.wgs84Lat.toDouble()
            Ypos = item.wgs84Lon.toDouble()
            name = item.dutyName
        }

        return binding.root
    }

    ////////////////////////////////상세정보///////////////////////////
    override fun onResume() {
        super.onResume()

    }
    ////////////////////////////////상세정보///////////////////////////


    ////////////////////////////지도//////////////////////////////
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
    ////////////////////////////지도///////////////////////////////


}