package com.kkt1019.hospitalinmyhand

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import com.google.android.gms.location.*
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.kkt1019.hospitalinmyhand.databinding.FragmentMapBinding


class MapFragment : Fragment() {

    var providerClient: FusedLocationProviderClient? = null

    private var locationRequest = LocationRequest()

    var mGoogleMap: GoogleMap? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return binding.root
    }

    override fun onResume() {
        super.onResume()

        //위치정보 제공자 객체얻어오기
        providerClient = LocationServices.getFusedLocationProviderClient(requireActivity())

        //위치 정보 요청 객체 생성 및 설정
        val locationRequest = LocationRequest.create()
        locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY //높은 정확도 우선시..[gps]

        locationRequest.interval = 5000 //5000ms[5초]간격으로 갱신

        //내 위치 실시간 갱신 요청
        if (ActivityCompat.checkSelfPermission(requireActivity(), Manifest.permission.ACCESS_FINE_LOCATION)
            != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission
                (requireActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
        ) { return }
        providerClient!!.requestLocationUpdates(locationRequest, locationCallback, Looper.getMainLooper()
        )
    }

    override fun onPause() {
        super.onPause()
        providerClient?.removeLocationUpdates(locationCallback)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //xml에 만들 지도프레그먼트 찾아오기
        val fragmentManager = childFragmentManager
        val mapFragment = fragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment!!.getMapAsync(OnMapReadyCallback { googleMap ->
            val seoul = LatLng(37.5663, 126.9779)
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(seoul, 16f)) //줌 1~25
            mGoogleMap = googleMap
            val settings = googleMap.uiSettings
            settings.isZoomControlsEnabled = true
            settings.isMyLocationButtonEnabled = true

            //내 위치 표시하기
            if (ActivityCompat.checkSelfPermission(requireActivity(), Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission
                    (requireActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
            ) { return@OnMapReadyCallback }
            googleMap.isMyLocationEnabled = true
        })
    }

    var locationCallback: LocationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {
            super.onLocationResult(locationResult)

            //파라미터로 전달된 위치정보결과 객체에게 위치정보를 얻어오기
            val location = locationResult.lastLocation
            val lat = location.latitude
            val lng = location.longitude

            G.Xpos = lat
            G.Ypos = lng

            Toast.makeText(context," " + G.Xpos+" // "+G.Ypos , Toast.LENGTH_SHORT).show()
        }
    }

    fun openMap(lat: Double, lng: Double) {
        val geouri: Uri = Uri.parse(String.format("geo:%f,%f", lat, lng))
        val geomap = Intent(Intent.ACTION_VIEW, geouri)
        geomap.setPackage("com.google.android.apps.maps") // 구글맵으로 열기
        this.startActivity(geomap)
    }

    val binding:FragmentMapBinding by lazy { FragmentMapBinding.inflate(layoutInflater) }



}