package com.kkt1019.hospitalinmyhand.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.kkt1019.hospitalinmyhand.ItemVO
import com.kkt1019.hospitalinmyhand.databinding.FragmentEmergencyBottomsheetBinding
import com.kkt1019.hospitalinmyhand.viewmodel.SharedViewModel
import net.daum.mf.map.api.MapPOIItem
import net.daum.mf.map.api.MapPoint
import net.daum.mf.map.api.MapView
import kotlin.properties.Delegates

class EmergencyBottomSheetFragment : BottomSheetDialogFragment() {

    val binding: FragmentEmergencyBottomsheetBinding by lazy { FragmentEmergencyBottomsheetBinding.inflate(layoutInflater) }
    var items = mutableListOf<ItemVO>()

    val mapView: MapView by lazy { MapView(context) }

    private val sharedViewModel: SharedViewModel by activityViewModels()

    lateinit var name : String
    lateinit var addr : String
    lateinit var tell1 : String
    lateinit var tell2: String
    var  Xpos by Delegates.notNull<Double>()
    var Ypos by Delegates.notNull<Double>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return binding.root
    }

    override fun onResume() {
        super.onResume()

        sharedViewModel.emergencyItem.observe(viewLifecycleOwner) {
            binding.title.text = it.dutyName
            binding.address.text = it.dutyAddr
            binding.tell1.text = it.dutyTel1
            binding.tell1.text = it.dutyTel3
        }

        binding.title.text = name
        binding.address.text = addr
        binding.tell1.text = tell1
        binding.tell2.text = tell2

        binding.mapView.addView(mapView)

    }


    fun detail(name: String, addr:String, tell1:String, tell2:String, Xpos:Double, Ypos:Double){

        this.name = name
        this.addr = addr
        this.tell1 = tell1
        this.tell2 = tell2
        this.Xpos = Xpos
        this.Ypos = Ypos

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val lat: Double = Xpos
        val lng: Double = Ypos

        var myMapPoint = MapPoint.mapPointWithGeoCoord(lat, lng)
        mapView.setMapCenterPointAndZoomLevel(myMapPoint, 4, true)
        mapView.zoomIn(true)
        mapView.zoomOut(true)
        Log.i("kim", "qweqweqe")


        val marker = MapPOIItem()

        Log.i("kim", "asdasdasdad")

        marker.apply {
            itemName = name
            mapPoint = myMapPoint
            markerType= MapPOIItem.MarkerType.BluePin
            Log.i("kim", "fghfghfgh")
        }
        mapView.addPOIItem(marker)
    }
}