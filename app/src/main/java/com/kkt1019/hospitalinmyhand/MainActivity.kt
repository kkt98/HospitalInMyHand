package com.kkt1019.hospitalinmyhand

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.cardview.widget.CardView
import androidx.drawerlayout.widget.DrawerLayout
import com.bumptech.glide.Glide
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.kkt1019.hospitalinmyhand.databinding.ActivityMainBinding
import de.hdodenhof.circleimageview.CircleImageView

class MainActivity : AppCompatActivity() {

    val binding:ActivityMainBinding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    var firebaseAuth: FirebaseAuth? = null

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

    }

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