package com.kkt1019.hospitalinmyhand

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.google.android.material.navigation.NavigationView
import com.kkt1019.hospitalinmyhand.databinding.ActivityMainBinding
import com.kkt1019.hospitalinmyhand.databinding.FragmentHomepage1BottomsheetBinding

class MainActivity : AppCompatActivity() {

    val binding:ActivityMainBinding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    val fragment:MutableList<Fragment> by lazy { mutableListOf() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        fragment.add(HomeFragment())
        fragment.add(BookMarkFragment())
        fragment.add(MedicalFragment())
        fragment.add(ReviewFragment())
        fragment.add(MapFragment())

        supportFragmentManager.beginTransaction().add(R.id.container, fragment[0]).commit()

        binding.bnv.setOnItemSelectedListener {

            supportFragmentManager.fragments.forEach {
                supportFragmentManager.beginTransaction().hide(it).commit()
            }

            val tran =  supportFragmentManager.beginTransaction()

            when(it.itemId){
                R.id.bnv_home -> {
                    tran.show(fragment[0])
                }
                R.id.bnv_bookmark -> {
                    if ( !supportFragmentManager.fragments.contains(fragment[1]))
                        tran.add(R.id.container, fragment[1])
                    tran.show(fragment[1])
                }
                R.id.bnv_medical -> {
                    if (!supportFragmentManager.fragments.contains(fragment[2]))
                        tran.add(R.id.container, fragment[2])
                    tran.show(fragment[2])
                }
                R.id.bnv_review -> {
                    if (!supportFragmentManager.fragments.contains(fragment[3]))
                        tran.add(R.id.container, fragment[3])
                    tran.show(fragment[3])
                }
                R.id.bnv_map -> {
                    if (!supportFragmentManager.fragments.contains(fragment[4]))
                        tran.add(R.id.container, fragment[4])
                    tran.show(fragment[4])
                }
            }
            tran.commit()

            drawlayout()

            true
        }

        //동적퍼미션
        val permissions = arrayOf(Manifest.permission.ACCESS_FINE_LOCATION)
        val checkResult = checkSelfPermission(permissions[0])
        if (checkResult == PackageManager.PERMISSION_DENIED) {
            requestPermissions(permissions, 10)
        }

    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == 10){
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Toast.makeText(this, "위치정보 사용 가능", Toast.LENGTH_SHORT).show()
            }else{
                Toast.makeText(this, "위치정보 사용 불가능", Toast.LENGTH_SHORT).show()
            }
        }

    }

    lateinit var drawerToggle: ActionBarDrawerToggle

    fun drawlayout(){

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        val drawerLayout = findViewById<DrawerLayout>(R.id.layout_drawer)

        drawerToggle = ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close)
        drawerToggle.syncState()
        drawerLayout.addDrawerListener(drawerToggle!!)

        val nav = findViewById<NavigationView>(R.id.nav)
        //네비게이션부의 아이템이 선택되었을때 반응하는 리스너
        //네비게이션부의 아이템이 선택되었을때 반응하는 리스너
        nav.setNavigationItemSelectedListener(NavigationView.OnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.menu_aa -> Toast.makeText(this@MainActivity, "aa", Toast.LENGTH_SHORT).show()


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