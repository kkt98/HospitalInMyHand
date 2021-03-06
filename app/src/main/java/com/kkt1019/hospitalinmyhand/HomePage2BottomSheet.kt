package com.kkt1019.hospitalinmyhand

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.kkt1019.hospitalinmyhand.databinding.FragmentHomepage2BottomsheetBinding
import net.daum.mf.map.api.MapPOIItem
import net.daum.mf.map.api.MapPoint
import net.daum.mf.map.api.MapView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.properties.Delegates

class HomePage2BottomSheet : BottomSheetDialogFragment() {

    val recycler : RecyclerView by lazy { binding.recycler }

    var items = mutableListOf<ItemVO>()

    val mapView: MapView by lazy { MapView(context) }

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

        binding.btn.setOnClickListener {

            val intent = Intent(context as Activity, RevieEdit::class.java)
            startActivity(intent)

        }

        return binding.root
    }

    override fun onResume() {
        super.onResume()

        datas()

        binding.title.text = name
        binding.address.text = addr
        binding.tell1.text = tell1
        binding.tell2.text = tell2

        binding.mapView.addView(mapView)

    }

    val binding: FragmentHomepage2BottomsheetBinding by lazy { FragmentHomepage2BottomsheetBinding.inflate(layoutInflater) }

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



    fun datas(){

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

                        Toast.makeText(context, G.uniqueid, Toast.LENGTH_SHORT).show()

                    }
                    binding.recycler.adapter?.notifyItemInserted(0)
                }
            }

            override fun onFailure(call: Call<ArrayList<ItemVO?>>, t: Throwable) {
//                Toast.makeText(context, "error : " + t.message, Toast.LENGTH_SHORT).show()

                //??????
                AlertDialog.Builder(context as Activity).setMessage(t.message).create().show()
            }
        })


        recycler.adapter = ReviewAdapter(activity as Context, items)

    }
}