package com.kkt1019.hospitalinmyhand.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.google.android.material.tabs.TabLayout
import com.kkt1019.hospitalinmyhand.R
import com.kkt1019.hospitalinmyhand.databinding.ActivityFavoriteBinding
import com.kkt1019.hospitalinmyhand.fragment.favoritefragment.FavoriteHospitalFragment
import com.kkt1019.hospitalinmyhand.fragment.favoritefragment.FavoriteEmergencyFragment
import com.kkt1019.hospitalinmyhand.fragment.favoritefragment.FavoritePharmacyFragment

class FavoriteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFavoriteBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_favorite)
        binding.activity = this

        binding.tabLayout.addTab(binding.tabLayout.newTab().setText("병원"))
        binding.tabLayout.addTab(binding.tabLayout.newTab().setText("응급실"))
        binding.tabLayout.addTab(binding.tabLayout.newTab().setText("약국"))

        supportFragmentManager.beginTransaction().replace(R.id.fragmentContainer, FavoriteHospitalFragment()).commit()

        // 탭 선택 리스너
        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                val fragment = when (tab.position) {
                    0 -> FavoriteHospitalFragment()
                    1 -> FavoriteEmergencyFragment()
                    2 -> FavoritePharmacyFragment()
                    else -> FavoriteHospitalFragment()
                }
                supportFragmentManager.beginTransaction().replace(R.id.fragmentContainer, fragment).commit()
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                // 탭이 선택 해제될 때 호출
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
                // 이미 선택된 탭이 다시 선택될 때 호출
            }
        })
    }

}
