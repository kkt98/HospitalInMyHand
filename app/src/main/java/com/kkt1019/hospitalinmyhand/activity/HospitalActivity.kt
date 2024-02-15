package com.kkt1019.hospitalinmyhand.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.kkt1019.hospitalinmyhand.fragment.HomePage1Fragment
import com.kkt1019.hospitalinmyhand.fragment.HomePage2Fragment
import com.kkt1019.hospitalinmyhand.R
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

    }

}