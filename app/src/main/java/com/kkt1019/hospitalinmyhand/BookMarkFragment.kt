package com.kkt1019.hospitalinmyhand

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.kkt1019.hospitalinmyhand.databinding.FragmentBookmarkBinding

class BookMarkFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return binding.root
    }


    val binding:FragmentBookmarkBinding by lazy { FragmentBookmarkBinding.inflate(layoutInflater) }

}