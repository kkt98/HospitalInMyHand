package com.kkt1019.hospitalinmyhand

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
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
import com.kkt1019.hospitalinmyhand.databinding.FragmentHomepage1BottomsheetBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.properties.Delegates

class HomePage1BottomSheet : BottomSheetDialogFragment() {

    val recycler : RecyclerView by lazy { binding.recycler }

    var items = mutableListOf<ItemVO>()

    lateinit var name : String
    lateinit var addr : String
    lateinit var tell : String
    lateinit var timeS : String
    lateinit var timeC : String
    var  Xpos by Delegates.notNull<Double>()
    var Ypos by Delegates.notNull<Double>()
    lateinit var medical : String
    lateinit var time2s : String
    lateinit var time2c : String

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
        binding.timeS.text = "평일 : " + timeS+" ~ "
        binding.timeC.text = timeC
        binding.time2S.text = "주말 : " + time2s+" ~ "
        binding.time2C.text = time2c

    }

    fun detail(name: String, addr:String, tell:String, timeS:String, timeC:String, Xpos:Double, Ypos:Double, medical:String, time2S:String, time2c:String){

        this.name = name
        this.addr = addr
        this.tell = tell
        this.timeS = timeS
        this.timeC = timeC
        this.Xpos = Xpos
        this.Ypos = Ypos
        this.medical = medical
        this.time2s = time2S
        this.time2c = time2c
    }
    ////////////////////////////////상세정보///////////////////////////


    ////////////////////////////지도///////////////////////////////
    var mGoogleMap: GoogleMap? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //위치 찾아오기
        val fragmentManager = childFragmentManager
        val mapFragment = fragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment!!.getMapAsync(OnMapReadyCallback { googleMap ->
            val seoul = LatLng(Xpos, Ypos)
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(seoul, 16f)) //줌 1~25
            mGoogleMap = googleMap
            val settings = googleMap.uiSettings
            settings.isZoomControlsEnabled = true
            settings.isMyLocationButtonEnabled = true

            //마커 표시
            val marker = MarkerOptions()
            marker.title(name)
            marker.position(seoul)
            marker.icon(BitmapDescriptorFactory.fromResource(R.drawable.marker)) //벡터이미지는 안됨. .png or .jpg만 됨

            marker.anchor(0.5f, 1.0f) //x축, y축


            mGoogleMap!!.addMarker(marker)
        })
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

                        Toast.makeText(context, G.uniqueid, Toast.LENGTH_SHORT).show()

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