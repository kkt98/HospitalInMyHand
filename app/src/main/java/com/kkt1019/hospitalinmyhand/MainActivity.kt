package com.kkt1019.hospitalinmyhand

import android.content.Intent
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.content.pm.Signature
import android.location.Location
import android.os.Bundle
import android.os.Looper
import android.util.Base64
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.cardview.widget.CardView
import androidx.core.app.ActivityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.bumptech.glide.Glide
import com.google.android.gms.location.*
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.kkt1019.hospitalinmyhand.databinding.ActivityMainBinding
import de.hdodenhof.circleimageview.CircleImageView
import okhttp3.internal.Util
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException

class MainActivity : AppCompatActivity() {

    val binding:ActivityMainBinding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    var firebaseAuth: FirebaseAuth? = null

    val providerClient: FusedLocationProviderClient by lazy { LocationServices.getFusedLocationProviderClient(this) }

    var mylocation: Location? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        drawlayout()

        Glide.with(this).load(R.drawable.mmedical).into(binding.gifHospital)
        Glide.with(this).load(R.drawable.map).into(binding.gifMap)
        Glide.with(this).load(R.drawable.medical).into(binding.gifMedical)
        Glide.with(this).load(R.drawable.favorit).into(binding.gifFav)
        Glide.with(this).load(R.drawable.hos).into(binding.gifPharmacy)
        Glide.with(this).load(R.drawable.calendar).into(binding.gifCalender)

        val cardview_hospital = findViewById<CardView>(R.id.hospital)
        cardview_hospital.setOnClickListener {
            val intent = Intent(this, HospitalActivity::class.java)
            startActivity(intent)
        }

        val cardview_medical = findViewById<CardView>(R.id.medical)
        cardview_medical.setOnClickListener {
            val intent = Intent(this, MedicalActivity::class.java)
            startActivity(intent)
        }

        val cardview_map = findViewById<CardView>(R.id.map)
        cardview_map.setOnClickListener {
            val intent = Intent(this, MapActivity::class.java)
            startActivity(intent)
        }

        val cardview_pharmacy = findViewById<CardView>(R.id.pharmacy)
        cardview_pharmacy.setOnClickListener {
            val intent = Intent(this, PharmacyActivity::class.java)
            startActivity(intent)
        }

        val cardview_favorite = findViewById<CardView>(R.id.fav)
        cardview_favorite.setOnClickListener {
            val intent = Intent(this, FavoritActivity::class.java)
            startActivity(intent)
        }

        val cardview_calender = findViewById<CardView>(R.id.calender)
        cardview_calender.setOnClickListener {
            val intent = Intent(this, CalendarActivity::class.java)
            startActivity(intent)
        }

        //위치 받아오기 퍼미션
        val permissions:Array<String> = arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION)
        if (checkSelfPermission(permissions[0]) == PackageManager.PERMISSION_DENIED){

            //퍼미션 요청 다이얼로그 보이기
            requestPermissions(permissions, 10)
        }else{
            //내위치 탐색 요청하는 기능 호출
            requestMyLocation()
        }

    }

    private fun requestMyLocation(){
        // 내위치 정보를 얻어오는 기능코드

        //위치검색 기준 설정값 객체
        val request: LocationRequest = LocationRequest.create()
        request.interval = 1000
        request.priority = Priority.PRIORITY_HIGH_ACCURACY //높은 정확도 우선

        //실시간 위치정보갱신을 요청
        if (ActivityCompat.checkSelfPermission(
                this,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                android.Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }
        providerClient.requestLocationUpdates(request, locationCallback, Looper.getMainLooper() )

    }

    //위치정보 검색결과 콜백객체
    private val locationCallback: LocationCallback = object : LocationCallback(){
        override fun onLocationResult(p0: LocationResult) {
            super.onLocationResult(p0)

            //갱신된 위치정보결과 객체에게 위치정보 얻어오기
            mylocation = p0.lastLocation

            G.Xpos = mylocation?.latitude.toString()
            G.Ypos = mylocation?.longitude.toString()

            //위치탐색이 끝났으니 내 위치 정보 업데이트 종료
//            providerClient.removeLocationUpdates(this) //this : locationCallback객체

        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == 10 && grantResults[0] == PackageManager.PERMISSION_GRANTED) requestMyLocation()
        else Toast.makeText(this, "내 위치정보를 제공하지않아 검색기능 사용불가😥", Toast.LENGTH_SHORT).show()
    }


    //네비게이션뷰
    lateinit var drawerToggle: ActionBarDrawerToggle

    fun drawlayout(){

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        toolbar.title = "내 근처 병원찾기"
        val drawerLayout = findViewById<DrawerLayout>(R.id.layout_drawer)

        val navi = findViewById<NavigationView>(R.id.nav)
        var tvName = navi.getHeaderView(0).findViewById<TextView>(R.id.header_tv_name)
        val ivProfile = navi.getHeaderView(0).findViewById<CircleImageView>(R.id.header_iv)
        tvName.text = G.nickname
        Glide.with(this).load(G.profileUrl).into(ivProfile)

        drawerToggle = ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close)
        drawerToggle.syncState()
        drawerLayout.addDrawerListener(drawerToggle!!)


        val nav = findViewById<NavigationView>(R.id.nav)
        //네비게이션부의 아이템이 선택되었을때 반응하는 리스너
        nav.setNavigationItemSelectedListener(NavigationView.OnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.menu_aa -> firebaseAuth?.signOut()

                R.id.menu_bb -> {

                    val intent = Intent(this, MyReview::class.java)

                    this.startActivity(intent)

                }
            }

            //Drawer 뷰를 닫기
            drawerLayout.closeDrawer(nav, true)
            false
        })

    }


}