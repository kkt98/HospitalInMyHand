package com.kkt1019.hospitalinmyhand

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
        val itemImage =intent.getStringExtra("itemImage")

        binding.atpnQesitm.text =atpnQesitm
        binding.efcyQesitm.text = efcyQesitm
        binding.entpName.text = entpName
        binding.itemName.text = itemName
        binding.seQesitm.text = seQesitm
        binding.useMethod.text = useMethodQesitm

        Glide.with(this).load(itemImage).into(binding.ivDetail)

        binding.ivDetail.transitionName = "zzz"

        supportActionBar!!.setDisplayHomeAsUpEnabled(true)


    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }
}