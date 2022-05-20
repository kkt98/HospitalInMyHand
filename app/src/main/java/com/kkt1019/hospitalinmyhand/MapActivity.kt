package com.kkt1019.hospitalinmyhand

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Looper
import android.util.AttributeSet
import android.view.View
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.*
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.kkt1019.hospitalinmyhand.databinding.ActivityMapBinding

class MapActivity : AppCompatActivity() {

    val binding: ActivityMapBinding by lazy { ActivityMapBinding.inflate(layoutInflater) }

    var providerClient: FusedLocationProviderClient? = null

    var mGoogleMap: GoogleMap? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)


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

        val fragmentManager = supportFragmentManager
        val mapFragment = fragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment!!.getMapAsync(OnMapReadyCallback { googleMap ->
            val seoul = LatLng(37.5663, 126.9779)
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(seoul, 16f)) //줌 1~25
            mGoogleMap = googleMap
            val settings = googleMap.uiSettings
            settings.isZoomControlsEnabled = true
            settings.isMyLocationButtonEnabled = true

            //내 위치 표시하기
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission
                    (this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
            ) { return@OnMapReadyCallback }
            googleMap.isMyLocationEnabled = true
        })
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
            val lat = location.latitude
            val lng = location.longitude

//            G.Xpos = lat
//            G.Ypos = lng
        }
    }
}