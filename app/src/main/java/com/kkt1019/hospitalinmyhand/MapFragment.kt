package com.kkt1019.hospitalinmyhand

import android.R
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.kkt1019.hospitalinmyhand.databinding.FragmentMapBinding


class MapFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

//        val view: View = inflater.inflate(R.layout., container, false)
//        activity!!.title = "주문관리"

        return binding.root
    }

    val binding:FragmentMapBinding by lazy { FragmentMapBinding.inflate(layoutInflater) }

}