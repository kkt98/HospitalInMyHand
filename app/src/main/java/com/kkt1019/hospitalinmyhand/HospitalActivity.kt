package com.kkt1019.hospitalinmyhand

import android.Manifest
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import android.widget.Toolbar
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.kkt1019.hospitalinmyhand.databinding.ActivityHospitalBinding

class HospitalActivity : AppCompatActivity() {
    val binding: ActivityHospitalBinding by lazy { ActivityHospitalBinding.inflate(layoutInflater) }

    val fragment:MutableList<Fragment> by lazy { mutableListOf() }

    var firebaseAuth: FirebaseAuth? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val toolbar = findViewById<androidx.appcompat.widget.Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        toolbar.title = "병원, 응급실"

        fragment.add(HomePage1Fragment())
        fragment.add(HomePage2Fragment())



        supportFragmentManager.beginTransaction().add(R.id.container, fragment[0]).commit()

        binding.bnv.setOnItemSelectedListener {

            supportFragmentManager.fragments.forEach {
                supportFragmentManager.beginTransaction().hide(it).commit()
            }

            val tran =  supportFragmentManager.beginTransaction()

            when(it.itemId){
                R.id.bnv_hospital -> {
                    tran.show(fragment[0])
                }
                R.id.bnv_hospital2 -> {
                    if ( !supportFragmentManager.fragments.contains(fragment[1]))
                        tran.add(R.id.container, fragment[1])
                    tran.show(fragment[1])
                }

            }
            tran.commit()

            true
        }

        //동적퍼미션
        val permissions = arrayOf(Manifest.permission.ACCESS_FINE_LOCATION)
        val checkResult = checkSelfPermission(permissions[0])
        if (checkResult == PackageManager.PERMISSION_DENIED) {
            requestPermissions(permissions, 10)
        }


        //동적퍼미션
        val permissionsImage = arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE)
        if (checkSelfPermission(permissionsImage[0]) == PackageManager.PERMISSION_DENIED) {
            requestPermissions(permissionsImage, 0)
        }else{
//            requestImagePermissons()
        }

    }

    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<out String>, grantResults: IntArray
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
}