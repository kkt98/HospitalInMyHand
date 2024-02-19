package com.kkt1019.hospitalinmyhand.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.kkt1019.hospitalinmyhand.databinding.ActivityMedicalMoreBinding

class MedicalMoreActivity : AppCompatActivity() {

    val binding : ActivityMedicalMoreBinding by lazy { ActivityMedicalMoreBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        binding.toolbar.title = "상세정보"

        val entpName =intent.getStringExtra("entpName")
        val itemName =intent.getStringExtra("itemName")
        val atpnQesitm =intent.getStringExtra("atpnQesitm")
        val efcyQesitm =intent.getStringExtra("efcyQesitm")
        val seQesitm =intent.getStringExtra("seQesitm")
        val useMethodQesitm =intent.getStringExtra("useMethodQesitm")

        binding.atpnQesitm.text = "주의사항 : " + atpnQesitm
        binding.efcyQesitm.text = "효능 : " + efcyQesitm
        binding.entpName.text = "제조사 : " + entpName
        binding.itemName.text = "제품명 : " + itemName
        binding.seQesitm.text = "부작용 : " + seQesitm
        binding.useMethod.text = "복용법 : " + useMethodQesitm

        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }
}