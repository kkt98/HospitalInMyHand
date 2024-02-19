package com.kkt1019.hospitalinmyhand.activity

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.kkt1019.hospitalinmyhand.adapter.MedicalAdapter
import com.kkt1019.hospitalinmyhand.R
import com.kkt1019.hospitalinmyhand.databinding.ActivityMedicalBinding
import com.kkt1019.hospitalinmyhand.viewmodel.MedicalViewModel

class MedicalActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMedicalBinding

    val recycler: RecyclerView by lazy { binding.recycler }

    private val medicalViewModel : MedicalViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_medical)
        binding.activity = this
        setSupportActionBar(binding.toolbar)

        binding.toolbar.title = "의약품 검색"

        binding.sflSample.stopShimmer()
        binding.sflSample.visibility = View.GONE

        medicalViewModel.medicalItems.observe(this, Observer { items ->
            binding.recycler.adapter = MedicalAdapter(this, items)
            binding.edit.text.clear()

            //아이템로딩 없애기
            binding.sflSample.stopShimmer()
            binding.sflSample.visibility = View.GONE
        })
    }

    fun onClickMedicalSearch() {
        //뷰모델 레트로핏 실행
        val etname = binding.edit.text.toString()
        medicalViewModel.fetchMedicalData(etname)

        //아이템 로딩 보여주기
        binding.sflSample.startShimmer()
        binding.sflSample.visibility = View.VISIBLE

        // 키보드 숨기기
        val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(binding.edit.windowToken, 0)
    }
}