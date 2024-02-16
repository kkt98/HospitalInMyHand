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
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.kkt1019.hospitalinmyhand.adapter.ReviewAdapter
import com.kkt1019.hospitalinmyhand.databinding.FragmentHomepage1BottomsheetBinding
import net.daum.mf.map.api.MapPOIItem
import net.daum.mf.map.api.MapPoint
import net.daum.mf.map.api.MapView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.properties.Delegates

class HomePage1BottomSheet : BottomSheetDialogFragment() {

    val recycler : RecyclerView by lazy { binding.recycler }

    var items = mutableListOf<ItemVO>()

    val mapView: MapView by lazy { MapView(context) }

    lateinit var name : String
    lateinit var addr : String
    lateinit var tell : String
    lateinit var timeS : String
    lateinit var timeC : String
    var  Xpos by Delegates.notNull<Double>()
    var Ypos by Delegates.notNull<Double>()
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

        binding.btn.setOnClickListener {

            val intent = Intent(context as Activity, RevieEdit::class.java)
            startActivity(intent)

        }

        return binding.root
    }

    ////////////////////////////////상세정보///////////////////////////
    override fun onResume() {
        super.onResume()

        datas()

        binding.title.text = name
        binding.tell.text = tell
        binding.address.text = addr
        binding.timeS.text = "월 : " + timeS+" ~ "
        binding.timeC.text = timeC
        binding.time1S.text = "화 : " + time1s+" ~ "
        binding.time1C.text = time1c
        binding.time3S.text = "수 : " + time2s+" ~ "
        binding.time3C.text = time2c
        binding.time2S.text = "목 : " + time3s+" ~ "
        binding.time2C.text = time3c
        binding.time4S.text = "금 : " + time4s+" ~ "
        binding.time4C.text = time4c
        binding.time5S.text = "토 : " + time5s+" ~ "
        binding.time5C.text = time5c

        binding.mapView.addView(mapView)

    }

    fun detail(name: String, addr:String, tell:String, timeS:String, timeC:String, Xpos:Double, Ypos:Double, medical:String, time2S:String, time2c:String,
               time1S:String, time1C:String, time3S:String, time3C:String, time4S:String, time4C:String, time5S:String, time5C:String){

        this.name = name
        this.addr = addr
        this.tell = tell
        this.timeS = timeS
        this.timeC = timeC
        this.Xpos = Xpos
        this.Ypos = Ypos
        this.medical = medical
        this.time1s = time1S
        this.time1c = time1C
        this.time2s = time2S
        this.time2c = time2c
        this.time3s = time3S
        this.time3c = time3C
        this.time4s = time4S
        this.time4c = time4C
        this.time5s = time5S
        this.time5c = time5C

    }
    ////////////////////////////////상세정보///////////////////////////


    ////////////////////////////지도///////////////////////////////
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val lat: Double = Xpos
        val lng: Double = Ypos

        var myMapPoint = MapPoint.mapPointWithGeoCoord(lat, lng)
        mapView.setMapCenterPointAndZoomLevel(myMapPoint, 4, true)
        mapView.zoomIn(true)
        mapView.zoomOut(true)

        val marker = MapPOIItem()
        marker.apply {
            itemName = name
            mapPoint = myMapPoint
            markerType= MapPOIItem.MarkerType.BluePin
        }
        mapView.addPOIItem(marker)
    }
    ////////////////////////////지도///////////////////////////////

    val binding: FragmentHomepage1BottomsheetBinding by lazy { FragmentHomepage1BottomsheetBinding.inflate(layoutInflater) }



    fun datas(){

        //서버에서 데이터를 불러오는 기능 메소드
        val retrofit = RetrofitHelper.getRetrofitInstanceGson()
        val retrofitService = retrofit!!.create(RetrofitService::class.java)
        val call = retrofitService.loadDataFromServer(G.uniqueid!!)
        call.enqueue(object : Callback<ArrayList<ItemVO?>> {
            override fun onResponse(call: Call<ArrayList<ItemVO?>>, response: Response<ArrayList<ItemVO?>>) {
                items.clear()
                binding.recycler.adapter?.notifyDataSetChanged()

                val list = response.body()!!
                for (ItemVO in list) {
                    if (ItemVO != null) {

                        items.add(0, ItemVO)

                    }
                    binding.recycler.adapter?.notifyItemInserted(0)
                }
            }

            override fun onFailure(call: Call<ArrayList<ItemVO?>>, t: Throwable) {
//                Toast.makeText(context, "error : " + t.message, Toast.LENGTH_SHORT).show()

                //확인
                AlertDialog.Builder(context as Activity).setMessage(t.message).create().show()
            }
        })

        recycler.adapter = ReviewAdapter(activity as Context, items)

    }
}