package com.kkt1019.hospitalinmyhand

import android.Manifest
import android.app.ActionBar
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.google.android.gms.location.*
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.kkt1019.hospitalinmyhand.databinding.FragmentHomeBinding

class HomeFragment: Fragment() {

    private lateinit var tabLayout: TabLayout
    private lateinit var viewPager: ViewPager2


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view:View = inflater.inflate(R.layout.fragment_home, container, false)
        tabLayout = view.findViewById(R.id.layout_tab)
        viewPager = view.findViewById(R.id.pager)

        val adapter = HomePageAdapter(this)
        viewPager.adapter = adapter

        val tabName = arrayOf("병원", "응급실", "약국")

        TabLayoutMediator(tabLayout, viewPager) {
                tab, position -> tab.text = tabName[position].toString()
        }.attach()

        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                viewPager.currentItem = tab!!.position
            }
            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }
            override fun onTabReselected(tab: TabLayout.Tab?) {

            }
        })

        return view
    }


    val binding: FragmentHomeBinding by lazy { FragmentHomeBinding.inflate(layoutInflater) }

}