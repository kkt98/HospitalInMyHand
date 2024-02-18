package com.kkt1019.hospitalinmyhand.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.kkt1019.hospitalinmyhand.ItemVO
import com.kkt1019.hospitalinmyhand.databinding.FragmentHospitalBottomsheetBinding
import com.kkt1019.hospitalinmyhand.viewmodel.SharedViewModel
import net.daum.mf.map.api.MapPOIItem
import net.daum.mf.map.api.MapPoint
import net.daum.mf.map.api.MapView

class HospitalBottomSheetFragment : BottomSheetDialogFragment() {

    val binding: FragmentHospitalBottomsheetBinding by lazy { FragmentHospitalBottomsheetBinding.inflate(layoutInflater) }
    var items = mutableListOf<ItemVO>()

    val mapView: MapView by lazy { MapView(context) }

    private val sharedViewModel: SharedViewModel by activityViewModels()

//    lateinit var name : String
    var  Xpos :Double = 0.0
    var Ypos :Double = 0.0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


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
//            name = it.dutyName
        }

        return binding.root
    }

    ////////////////////////////////상세정보///////////////////////////
    override fun onResume() {
        super.onResume()

        binding.mapView.addView(mapView)

    }
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