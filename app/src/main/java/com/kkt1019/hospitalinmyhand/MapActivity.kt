package com.kkt1019.hospitalinmyhand

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Looper
import android.view.View
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.*
import com.google.android.gms.maps.GoogleMap
import com.kkt1019.hospitalinmyhand.databinding.ActivityMapBinding
import net.daum.mf.map.api.MapPOIItem
import net.daum.mf.map.api.MapPoint
import net.daum.mf.map.api.MapView

class MapActivity : AppCompatActivity() {

    val binding: ActivityMapBinding by lazy { ActivityMapBinding.inflate(layoutInflater) }

    var providerClient: FusedLocationProviderClient? = null

    var searchQurey:String = "병원"

    var mapView: MapView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val toolbar = findViewById<androidx.appcompat.widget.Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        toolbar.title = "지도"

        kakaoMap()

        binding.etSearch.setOnEditorActionListener { textView, i, keyEvent ->

            searchQurey = binding.etSearch.text.toString()
            //카카오 장소검색 API 작업요청
            searchPlaces()

            false
        }

        setChoiceButtonsListener()
    }

    fun kakaoMap(){
        //맵뷰객체 생성하기
        mapView = MapView(this)

        //xml에 있는 MapView의 컨테이너용 뷰(RelativeLayout)에 맵뷰를 추가
        val mapViewContainer = findViewById<RelativeLayout>(R.id.map_view)
        mapViewContainer.addView(mapView)

        //중심점 변경 -지도 위치
        // 중심점 변경
        mapView!!.setMapCenterPoint(MapPoint.mapPointWithGeoCoord(37.53737528, 127.00557633), true)

        // 줌 레벨 변경
        mapView!!.setZoomLevel(7, true)

        // 중심점 변경 + 줌 레벨 변경
        mapView!!.setMapCenterPointAndZoomLevel(
            MapPoint.mapPointWithGeoCoord(33.41, 126.52),
            9,
            true
        )

        // 줌 인
        mapView!!.zoomIn(true)

        // 줌 아웃
        mapView!!.zoomOut(true)

        val point = MapPoint.mapPointWithGeoCoord(37.5, 127.5)
        val marker = MapPOIItem()
        marker.itemName = "Default Marker"
        marker.tag = 0
        marker.mapPoint = point
        marker.markerType = MapPOIItem.MarkerType.BluePin // 기본으로 제공하는 BluePin 마커 모양.

        marker.selectedMarkerType =
            MapPOIItem.MarkerType.RedPin // 마커를 클릭했을때, 기본으로 제공하는 RedPin 마커 모양.


        mapView!!.addPOIItem(marker)
    }

    override fun onResume() {
        super.onResume()

        //위치정보 제공자 객체얻어오기
        providerClient = LocationServices.getFusedLocationProviderClient(this)

        //위치 정보 요청 객체 생성 및 설정
        val locationRequest = LocationRequest.create()
        locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY //높은 정확도 우선시..[gps]

        locationRequest.interval = 5000 //5000ms[5초]간격으로 갱신

        //내 위치 실시간 갱신 요청
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
            != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission
                (this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
        ) { return }
        providerClient!!.requestLocationUpdates(locationRequest, locationCallback, Looper.getMainLooper()
        )

    }

    override fun onPause() {
        super.onPause()
        providerClient?.removeLocationUpdates(locationCallback)
    }



    var locationCallback: LocationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {
            super.onLocationResult(locationResult)

            //파라미터로 전달된 위치정보결과 객체에게 위치정보를 얻어오기
            val location = locationResult.lastLocation
            val lat = location?.latitude
            val lng = location?.longitude

//            G.Xpos = lat
//            G.Ypos = lng
        }
    }

    private fun setChoiceButtonsListener(){

        binding.choiceHospital.setOnClickListener { clickChoice(it) }
        binding.choiceEmergency.setOnClickListener { clickChoice(it) }
        binding.choicePill.setOnClickListener { clickChoice(it) }

    }

    var choiceId = R.id.choice_hospital

    private fun clickChoice(view: View){

        //기존 선택된 뷰의 배경이미지 변경
        findViewById<ImageView>(choiceId).setBackgroundResource(R.drawable.bg_choice)

        //현재 선택된 뷰의 배경 이미지를 변경
        view.setBackgroundResource(R.drawable.bg_choice_select)

        //현재 선택한 뷰의 id를 멤버변수에 저장
        choiceId = view.id

        //선택한것에 따라서 검색장소 키워드 변경해 다시요청
        when (choiceId){

            R.id.choice_hospital -> searchQurey = "병원"
            R.id.choice_emergency -> searchQurey = "응급실"
            R.id.choice_pill -> searchQurey = "약국"


        }
        //검색요청
        searchPlaces()

        //검색창에 글씨가 있으면 지우기
        binding.etSearch.text.clear()
        binding.etSearch.clearFocus() //이전 포커스로인해 커서가 남아있을 수 있어서 포커스 없애기

    }

    private fun searchPlaces(){
        Toast.makeText(this, "$searchQurey", Toast.LENGTH_SHORT).show()

        //레트로핏을 이용하여 카카오 키워드 장소검색 API 파싱하기

    }
}