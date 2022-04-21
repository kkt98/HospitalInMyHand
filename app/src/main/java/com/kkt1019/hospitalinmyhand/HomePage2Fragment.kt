package com.kkt1019.hospitalinmyhand

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.fragment.app.Fragment
import com.kkt1019.hospitalinmyhand.databinding.FragmentHomePage2Binding

class HomePage2Fragment:Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding.btn.setOnClickListener { spinner() }

        return binding.root
    }

    val binding:FragmentHomePage2Binding by lazy { FragmentHomePage2Binding.inflate(layoutInflater) }

    fun spinner(){

        val mDialogView = LayoutInflater.from(context).inflate(R.layout.dialog2, null)

        var spinner = mDialogView.findViewById<Spinner>(R.id.spinner)
        var spinner2 = mDialogView.findViewById<Spinner>(R.id.spinner2)

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {

                when (p2){

                    0 -> {

                        val items = resources.getStringArray(R.array.spinner_region_seoul)
                        val madapter = ArrayAdapter(activity as Context, android.R.layout.simple_spinner_dropdown_item, items)
                        spinner2.adapter = madapter

                    }
                    1 -> {

                        val items = resources.getStringArray(R.array.spinner_region_busan)
                        val madapter = ArrayAdapter(activity as Context, android.R.layout.simple_spinner_dropdown_item, items)
                        spinner2.adapter = madapter

                    }
                    2 -> {

                        val items = resources.getStringArray(R.array.spinner_region_daegu)
                        val madapter = ArrayAdapter(activity as Context, android.R.layout.simple_spinner_dropdown_item, items)
                        spinner2.adapter = madapter

                    }
                    3 -> {

                        val items = resources.getStringArray(R.array.spinner_region_incheon)
                        val madapter = ArrayAdapter(activity as Context, android.R.layout.simple_spinner_dropdown_item, items)
                        spinner2.adapter = madapter

                    }
                    4 -> {

                        val items = resources.getStringArray(R.array.spinner_region_gwangju)
                        val madapter = ArrayAdapter(activity as Context, android.R.layout.simple_spinner_dropdown_item, items)
                        spinner2.adapter = madapter

                    }
                    5 -> {

                        val items = resources.getStringArray(R.array.spinner_region_daejeon)
                        val madapter = ArrayAdapter(activity as Context, android.R.layout.simple_spinner_dropdown_item, items)
                        spinner2.adapter = madapter

                    }
                    6 -> {

                        val items = resources.getStringArray(R.array.spinner_region_ulsan)
                        val madapter = ArrayAdapter(activity as Context, android.R.layout.simple_spinner_dropdown_item, items)
                        spinner2.adapter = madapter

                    }
                    7 -> {

                        val items = resources.getStringArray(R.array.spinner_region_sejong)
                        val madapter = ArrayAdapter(activity as Context, android.R.layout.simple_spinner_dropdown_item, items)
                        spinner2.adapter = madapter

                    }
                    8 -> {

                        val items = resources.getStringArray(R.array.spinner_region_gyeonggi)
                        val madapter = ArrayAdapter(activity as Context, android.R.layout.simple_spinner_dropdown_item, items)
                        spinner2.adapter = madapter

                    }
                    9 -> {

                        val items = resources.getStringArray(R.array.spinner_region_gangwon)
                        val madapter = ArrayAdapter(activity as Context, android.R.layout.simple_spinner_dropdown_item, items)
                        spinner2.adapter = madapter

                    }
                    10 -> {

                        val items = resources.getStringArray(R.array.spinner_region_chung_buk)
                        val madapter = ArrayAdapter(activity as Context, android.R.layout.simple_spinner_dropdown_item, items)
                        spinner2.adapter = madapter

                    }
                    11 -> {

                        val items = resources.getStringArray(R.array.spinner_region_chung_nam)
                        val madapter = ArrayAdapter(activity as Context, android.R.layout.simple_spinner_dropdown_item, items)
                        spinner2.adapter = madapter

                    }
                    12 -> {

                        val items = resources.getStringArray(R.array.spinner_region_jeon_buk)
                        val madapter = ArrayAdapter(activity as Context, android.R.layout.simple_spinner_dropdown_item, items)
                        spinner2.adapter = madapter

                    }
                    13 -> {

                        val items = resources.getStringArray(R.array.spinner_region_jeon_nam)
                        val madapter = ArrayAdapter(activity as Context, android.R.layout.simple_spinner_dropdown_item, items)
                        spinner2.adapter = madapter

                    }
                    14 -> {

                        val items = resources.getStringArray(R.array.spinner_region_gyeong_buk)
                        val madapter = ArrayAdapter(activity as Context, android.R.layout.simple_spinner_dropdown_item, items)
                        spinner2.adapter = madapter

                    }
                    15 -> {

                        val items = resources.getStringArray(R.array.spinner_region_gyeong_nam)
                        val madapter = ArrayAdapter(activity as Context, android.R.layout.simple_spinner_dropdown_item, items)
                        spinner2.adapter = madapter

                    }
                    16 -> {

                        val items = resources.getStringArray(R.array.spinner_jeju)
                        val madapter = ArrayAdapter(activity as Context, android.R.layout.simple_spinner_dropdown_item, items)
                        spinner2.adapter = madapter

                    }
                }
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                Log.d("MyTag", "onNothingSelected")
            }

        }

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