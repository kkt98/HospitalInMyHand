package com.kkt1019.hospitalinmyhand

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import com.kkt1019.hospitalinmyhand.databinding.FragmentHomePage1Binding

class HomePage1Fragment:Fragment() {



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding.btn.setOnClickListener { spinner() }

        return binding.root
    }
        val binding: FragmentHomePage1Binding by lazy { FragmentHomePage1Binding.inflate(layoutInflater) }



    fun spinner(){

        val mDialogView = LayoutInflater.from(context).inflate(R.layout.dialog, null)

        var spinner = mDialogView.findViewById<Spinner>(R.id.spinner)

        val mBuilder = AlertDialog.Builder(context)
            .setView(mDialogView)

        mBuilder.setPositiveButton("확인", null)
        mBuilder.setNegativeButton("취소", null)

        mBuilder.show()

        val items = resources.getStringArray(R.array.city)

        val madapter = ArrayAdapter(activity as Context, android.R.layout.simple_spinner_dropdown_item, items)

        spinner.adapter = madapter
    }
}