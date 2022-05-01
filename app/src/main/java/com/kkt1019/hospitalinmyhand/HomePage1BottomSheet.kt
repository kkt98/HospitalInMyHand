package com.kkt1019.hospitalinmyhand

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.location.*
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.kkt1019.hospitalinmyhand.databinding.FragmentHomepage1BottomsheetBinding
import kotlin.properties.Delegates

class HomePage1BottomSheet : BottomSheetDialogFragment() {

    val recycler : RecyclerView by lazy { binding.recycler }

    var items = mutableListOf<ReviewItem>()

    lateinit var name : String
    lateinit var addr : String
    lateinit var tell : String
    lateinit var timeS : String
    lateinit var timeC : String
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
        binding.tell.text = tell
        binding.address.text = addr

    }

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

    val binding: FragmentHomepage1BottomsheetBinding by lazy { FragmentHomepage1BottomsheetBinding.inflate(layoutInflater) }




    fun detail(name: String, addr:String, tell:String, timeS:String, timeC:String, Xpos:Double, Ypos:Double){

        this.name = name
        this.addr = addr
        this.tell = tell
        this.timeS = timeS
        this.timeC = timeC
        this.Xpos = Xpos
        this.Ypos = Ypos

    }



    fun datas(){

        items.add( ReviewItem(R.drawable.koala, "아이디", R.drawable.frog, "asdasdasdasdasd\nasdasdasdasdasd\nasdasdasdasd\nasdasdasdasd"))
        items.add( ReviewItem(R.drawable.koala, "아이디", R.drawable.frog, "후기 내용"))
        items.add( ReviewItem(R.drawable.koala, "아이디", R.drawable.frog, "후기 내용"))
        items.add( ReviewItem(R.drawable.koala, "아이디", R.drawable.frog, "후기 내용"))
        items.add( ReviewItem(R.drawable.koala, "아이디", R.drawable.frog, "후기 내용"))

        recycler.adapter = ReviewAdapter(activity as Context, items)

    }
}