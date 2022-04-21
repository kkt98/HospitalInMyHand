package com.kkt1019.hospitalinmyhand

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.kkt1019.hospitalinmyhand.databinding.FragmentHomeBinding

class HomeFragment: Fragment() {

    private lateinit var tabLayout: TabLayout
    private lateinit var viewPager: ViewPager2

    companion object {
        fun newInstance(): HomeFragment = HomeFragment()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
    }

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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

//    var tabTitle = arrayOf("병원", "응급실", "약국")

//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//
//        var layoutTab = binding.layoutTab.findViewById<TabLayout>(R.id.layout_tab)
//        var pager = binding.layoutTab.findViewById<ViewPager2>(R.id.pager)
//
//
//        //뷰 페이저와 TabLayout 을 연동하기******************
//        //연동을 하면 자동으로 뷰페이저의 페이지 개수만큼 Tab버튼이 만들어짐********************
//        //ViewPager 와 TabLayout 와 중재자 역할의 객체 생성
//        TabLayoutMediator(layoutTab, pager)
//
//            { tab, position -> //페이지 개수만큼 이 메소드가 발동함.
//            //첫번쨰 파라미터 : 자동으로 만들어질 Tab객체
//            //두번째 파라미터 : 만들번째 위치 position
//            tab.setText(tabTitle.get(position))
//
//        }.attach() // .attach() 를 잊지말고 호출
//
//
//        super.onViewCreated(view, savedInstanceState)
//    }

    val binding: FragmentHomeBinding by lazy { FragmentHomeBinding.inflate(layoutInflater) }

}