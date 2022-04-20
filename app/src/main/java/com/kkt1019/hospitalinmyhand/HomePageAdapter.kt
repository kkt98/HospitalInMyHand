package com.kkt1019.hospitalinmyhand

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter

class HomePageAdapter(fragmentActivity: HomeFragment): FragmentStateAdapter(fragmentActivity){

    private val NUM_PAGES = 3

    override fun getItemCount(): Int = NUM_PAGES

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> HomePage1Fragment()
            1 -> HomePage2Fragment()
            2 -> HomePage3Fragment()
            else -> HomePage1Fragment()
        }

    }

}