package com.kkt1019.hospitalinmyhand.activity

import android.os.Bundle
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.kkt1019.hospitalinmyhand.data.KakaoSearchPlaceItemVO
import com.kkt1019.hospitalinmyhand.R
import com.kkt1019.hospitalinmyhand.data.ShareData
import com.kkt1019.hospitalinmyhand.databinding.ActivityMapBinding
import com.kkt1019.hospitalinmyhand.viewmodel.MapViewModel
import net.daum.mf.map.api.MapPOIItem
import net.daum.mf.map.api.MapPoint
import net.daum.mf.map.api.MapView

class MapActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMapBinding
    private lateinit var mapView: MapView
    var searchQuery: String = "병원"
    private lateinit var myLocationMapPoint: MapPoint // "내 위치" 마커의 MapPoint를 저장할 변수 선언
    private val mapViewModel: MapViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_map)  // 수정된 부분
        binding.activity = this

        val toolbar = findViewById<androidx.appcompat.widget.Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        toolbar.title = "지도"

        mapView = MapView(this)  // mapView를 여기서 초기화
        val mapViewContainer = binding.mapView as ViewGroup
        mapViewContainer.addView(mapView)

        myLocationMarker()

        mapViewModel.searchPlaceResponse.observe(this) { response ->
            if (response != null) {
                removeAllMarkersExceptMyLocation()
                kakaoMarkers(response)
            } else {
                Toast.makeText(this, "통신 오류 다시 시도해주세요.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun kakaoMarkers(response: KakaoSearchPlaceItemVO) {
        response.documents.forEach { document ->
            val marker = MapPOIItem().apply {
                itemName = document.place_name
                mapPoint = MapPoint.mapPointWithGeoCoord(document.y.toDouble(), document.x.toDouble())
                markerType = MapPOIItem.MarkerType.YellowPin
                selectedMarkerType = MapPOIItem.MarkerType.RedPin
            }
            mapView.addPOIItem(marker)
        }
    }

    private fun myLocationMarker() {
        val lat: Double = ShareData.lat
        val lng: Double = ShareData.lng

        myLocationMapPoint = MapPoint.mapPointWithGeoCoord(lat, lng) // "내 위치" 마커의 MapPoint 저장
        mapView.setMapCenterPointAndZoomLevel(myLocationMapPoint, 4, true)

        val marker = MapPOIItem().apply {
            itemName = "내 위치"
            mapPoint = myLocationMapPoint
            markerType = MapPOIItem.MarkerType.CustomImage
            customImageResourceId = R.drawable.mylocation40
        }
        mapView.addPOIItem(marker)
    }

    private fun removeAllMarkersExceptMyLocation() {
        mapView.poiItems.filter { it.mapPoint != myLocationMapPoint }.forEach { mapView.removePOIItem(it) }
    }

    fun clickChoice(int: Int) {
        when (int) {
            1 -> searchQuery = "병원"
            2 -> searchQuery = "응급실"
            3 -> searchQuery = "약국"
        }
        mapViewModel.searchPlaces(searchQuery, ShareData.lng.toString(), ShareData.lat.toString())
    }
}