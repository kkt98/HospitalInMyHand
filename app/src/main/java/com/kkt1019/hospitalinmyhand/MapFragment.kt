package com.kkt1019.hospitalinmyhand

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import com.kkt1019.hospitalinmyhand.databinding.FragmentBookmarkBinding
import com.kkt1019.hospitalinmyhand.databinding.FragmentMapBinding

class MapFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        return binding.root
    }

    val binding:FragmentMapBinding by lazy { FragmentMapBinding.inflate(layoutInflater) }

}