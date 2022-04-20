package com.kkt1019.hospitalinmyhand

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.kkt1019.hospitalinmyhand.databinding.FragmentHomePage2Binding

class HomePage2Fragment:Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return binding.root
    }

    val binding:FragmentHomePage2Binding by lazy { FragmentHomePage2Binding.inflate(layoutInflater) }

}