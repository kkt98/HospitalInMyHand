package com.kkt1019.hospitalinmyhand.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.kkt1019.hospitalinmyhand.fragment.HospitalFragment
import com.kkt1019.hospitalinmyhand.fragment.EmergencyFragment
import com.kkt1019.hospitalinmyhand.R
import com.kkt1019.hospitalinmyhand.databinding.ActivityHospitalBinding
import com.kkt1019.hospitalinmyhand.viewmodel.SharedViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HospitalActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHospitalBinding
    private lateinit var viewModel: SharedViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHospitalBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val toolbar = findViewById<androidx.appcompat.widget.Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        toolbar.title = "병원, 응급실"

        viewModel = ViewModelProvider(this)[SharedViewModel::class.java]

        setupBottomNavigationView()
        if (savedInstanceState == null) {
            // 앱 처음 실행 시 첫 번째 프래그먼트를 표시
            showFragment(HospitalFragment(), "HOME_1")
        }
    }

    private fun setupBottomNavigationView() {
        binding.bnv.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.bnv_hospital -> showFragment(HospitalFragment(), "HOME_1")
                R.id.bnv_hospital2 -> showFragment(EmergencyFragment(), "HOME_2")
            }
            true
        }
    }

    private fun showFragment(fragment: Fragment, tag: String) {
        val manager = supportFragmentManager
        val transaction = manager.beginTransaction()
        var currentFragment = manager.findFragmentByTag(tag)

        // 모든 프래그먼트를 숨김
        manager.fragments.forEach {
            if (it != null && it.isVisible) transaction.hide(it)
        }

        if (currentFragment == null) {
            // 태그로 프래그먼트를 찾을 수 없으면 새로 추가
            currentFragment = fragment
            transaction.add(R.id.container, currentFragment, tag)
        } else {
            // 프래그먼트가 이미 있으면 보여줌
            transaction.show(currentFragment)
        }

        transaction.commit()
    }
}
