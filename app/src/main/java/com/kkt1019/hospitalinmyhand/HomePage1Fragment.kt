package com.kkt1019.hospitalinmyhand

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.kkt1019.hospitalinmyhand.databinding.FragmentHomePage1Binding

class HomePage1Fragment:Fragment() {

    lateinit var binding:FragmentHomePage1Binding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentHomePage1Binding.inflate(inflater, container, false)

        return binding.root
    }

}