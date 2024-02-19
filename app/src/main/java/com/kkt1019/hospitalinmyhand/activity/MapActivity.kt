package com.kkt1019.hospitalinmyhand.activity

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.kkt1019.hospitalinmyhand.KakaoSearchPlaceItemVO
import com.kkt1019.hospitalinmyhand.R
import com.kkt1019.hospitalinmyhand.network.RetrofitHelper
import com.kkt1019.hospitalinmyhand.network.RetrofitService
import com.kkt1019.hospitalinmyhand.data.ShareData
import com.kkt1019.hospitalinmyhand.databinding.ActivityMapBinding
import net.daum.mf.map.api.MapPOIItem
import net.daum.mf.map.api.MapPoint
import net.daum.mf.map.api.MapView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MapActivity : AppCompatActivity() {

    val binding: ActivityMapBinding by lazy { ActivityMapBinding.inflate(layoutInflater) }
    var searchQurey:String = "병원"
    val mapView:MapView by lazy { MapView(this) }
    var searchPlaceResponse: KakaoSearchPlaceItemVO? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val toolbar = findViewById<androidx.appcompat.widget.Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        toolbar.title = "지도"

        searchPlaces()

        setChoiceButtonsListener()

        binding.mapView.addView(mapView)

        kakaoMapMaker()

    }

    private fun searchPlaces(){
        Toast.makeText(this, "$searchQurey", Toast.LENGTH_SHORT).show()
        //레트로핏을 이용하여 카카오 키워드 장소검색 API 파싱하기

        val retrofit = RetrofitHelper.getRetrofitKakaoLocation()
        retrofit.create(RetrofitService::class.java).searchPlaces(searchQurey, ShareData.lat.toString(), ShareData.lng.toString())
            .enqueue(object : Callback<KakaoSearchPlaceItemVO>{
                override fun onResponse(
                    call: Call<KakaoSearchPlaceItemVO>,
                    response: Response<KakaoSearchPlaceItemVO>
                ) {
                    searchPlaceResponse = response.body()
                    Log.i("kim", "$searchPlaceResponse")

//                    Toast.makeText(this@MapActivity, "${searchPlace}", Toast.LENGTH_SHORT).show()
                }

                override fun onFailure(call: Call<KakaoSearchPlaceItemVO>, t: Throwable) {
                    Toast.makeText(this@MapActivity, "에러 : $t", Toast.LENGTH_SHORT).show()
                }

            })

        kakaoMakers()

    }

    val markers = MapPOIItem()

    fun kakaoMakers(){
        val documents = searchPlaceResponse?.documents

        documents?.forEach {

            val point:MapPoint = MapPoint.mapPointWithGeoCoord(it.y.toDouble(), it.x.toDouble())

            //마커객체 생성
            markers.apply {

                itemName = it.place_name

                mapPoint = point
                markerType = MapPOIItem.MarkerType.YellowPin

                selectedMarkerType = MapPOIItem.MarkerType.RedPin

            }

            mapView.addPOIItem(markers)

        }
    }

    fun kakaoMapMaker(){

        val lat: Double = ShareData.lat
        val lng: Double = ShareData.lng

        var myMapPoint = MapPoint.mapPointWithGeoCoord(lat, lng)
        mapView.setMapCenterPointAndZoomLevel(myMapPoint, 4, true)
        mapView.zoomIn(true)
        mapView.zoomOut(true)
        Log.i("kim", "qweqweqe")


        val marker = MapPOIItem()

        Log.i("kim", "asdasdasdad")

        marker.apply {
            itemName = "내 위치"
            mapPoint = myMapPoint
            markerType=MapPOIItem.MarkerType.CustomImage
            customImageResourceId= R.drawable.mylocation40
            Log.i("kim", "fghfghfgh")

        }
        mapView.addPOIItem(marker)
    }

    private fun setChoiceButtonsListener(){

        binding.choiceHospital.setOnClickListener { clickChoice(it) }
        binding.choiceEmergency.setOnClickListener { clickChoice(it) }
        binding.choicePill.setOnClickListener { clickChoice(it) }

    }

    var choiceId = R.id.choice_hospital

    private fun clickChoice(view: View){

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

    }
}