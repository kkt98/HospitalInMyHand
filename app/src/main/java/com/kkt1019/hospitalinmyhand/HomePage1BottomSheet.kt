package com.kkt1019.hospitalinmyhand

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.kkt1019.hospitalinmyhand.adapter.ReviewAdapter
import com.kkt1019.hospitalinmyhand.databinding.FragmentHomepage1BottomsheetBinding
import com.kkt1019.hospitalinmyhand.viewmodel.SharedViewModel
import net.daum.mf.map.api.MapPOIItem
import net.daum.mf.map.api.MapPoint
import net.daum.mf.map.api.MapView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.properties.Delegates

class HomePage1BottomSheet : BottomSheetDialogFragment() {

    val binding: FragmentHomepage1BottomsheetBinding by lazy { FragmentHomepage1BottomsheetBinding.inflate(layoutInflater) }
    var items = mutableListOf<ItemVO>()

    val mapView: MapView by lazy { MapView(context) }

    private val sharedViewModel: SharedViewModel by activityViewModels()

//    lateinit var name : String
    lateinit var addr : String
    lateinit var tell : String
    lateinit var timeS : String
    lateinit var timeC : String
    var  Xpos :Double = 0.0
    var Ypos :Double = 0.0
    lateinit var medical : String
    lateinit var time1s : String
    lateinit var time1c : String
    lateinit var time2s : String
    lateinit var time2c : String
    lateinit var time3s : String
    lateinit var time3c : String
    lateinit var time4s : String
    lateinit var time4c : String
    lateinit var time5s : String
    lateinit var time5c : String

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        sharedViewModel.hospitalItem.observe(viewLifecycleOwner) { item ->
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
//            name = it.dutyName
        }

        return binding.root
    }

    ////////////////////////////////상세정보///////////////////////////
    override fun onResume() {
        super.onResume()

        binding.mapView.addView(mapView)

    }

//    fun detail(name: String, addr:String, tell:String, timeS:String, timeC:String, Xpos:Double, Ypos:Double, medical:String, time2S:String, time2c:String,
//               time1S:String, time1C:String, time3S:String, time3C:String, time4S:String, time4C:String, time5S:String, time5C:String){
//
//        this.name = name
//        this.addr = addr
//        this.tell = tell
//        this.timeS = timeS
//        this.timeC = timeC
//        this.Xpos = Xpos
//        this.Ypos = Ypos
//        this.medical = medical
//        this.time1s = time1S
//        this.time1c = time1C
//        this.time2s = time2S
//        this.time2c = time2c
//        this.time3s = time3S
//        this.time3c = time3C
//        this.time4s = time4S
//        this.time4c = time4C
//        this.time5s = time5S
//        this.time5c = time5C
//
//    }
    ////////////////////////////////상세정보///////////////////////////


    ////////////////////////////지도///////////////////////////////
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val lat: Double = Xpos
        val lng: Double = Ypos

        val myMapPoint = MapPoint.mapPointWithGeoCoord(lat, lng)
        mapView.setMapCenterPointAndZoomLevel(myMapPoint, 4, true)
        mapView.zoomIn(true)
        mapView.zoomOut(true)

        val marker = MapPOIItem()
        marker.apply {
//            itemName = name
            mapPoint = myMapPoint
            markerType= MapPOIItem.MarkerType.BluePin
        }
        mapView.addPOIItem(marker)
    }
    ////////////////////////////지도///////////////////////////////


}