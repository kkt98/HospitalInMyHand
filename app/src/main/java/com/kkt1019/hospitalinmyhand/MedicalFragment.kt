package com.kkt1019.hospitalinmyhand

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.kkt1019.hospitalinmyhand.databinding.FragmentBookmarkBinding
import com.kkt1019.hospitalinmyhand.databinding.FragmentMedicalBinding

class MedicalFragment : Fragment() {

    val recycler:RecyclerView by lazy { binding.recycler }

    var items = mutableListOf<MedicalItem>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        datas()

        return binding.root
    }

    val binding:FragmentMedicalBinding by lazy { FragmentMedicalBinding.inflate(layoutInflater) }

    fun datas(){

        items.add( MedicalItem("제조사", "약 이름", R.drawable.frog))
        items.add( MedicalItem("제조사", "약 이름", R.drawable.frog))
        items.add( MedicalItem("제조사", "약 이름", R.drawable.frog))
        items.add( MedicalItem("제조사", "약 이름", R.drawable.frog))
        items.add( MedicalItem("제조사", "약 이름", R.drawable.frog))

        recycler.adapter = MedicalAdapter(activity as Context, items)

    }

}