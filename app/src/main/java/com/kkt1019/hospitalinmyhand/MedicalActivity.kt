package com.kkt1019.hospitalinmyhand

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.RecyclerView
import com.kkt1019.hospitalinmyhand.databinding.ActivityMedicalBinding

class MedicalActivity : AppCompatActivity() {
    val binding: ActivityMedicalBinding by lazy { ActivityMedicalBinding.inflate(layoutInflater) }

    val recycler: RecyclerView by lazy { binding.recycler }

    var items = mutableListOf<MedicalItem>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val toolbar = findViewById<androidx.appcompat.widget.Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        toolbar.title = "약 검색"

        datas()
    }

    fun datas(){

        items.add( MedicalItem("제조사", "약 이름", R.drawable.frog))
        items.add( MedicalItem("제조사", "약 이름", R.drawable.frog))
        items.add( MedicalItem("제조사", "약 이름", R.drawable.frog))
        items.add( MedicalItem("제조사", "약 이름", R.drawable.frog))
        items.add( MedicalItem("제조사", "약 이름", R.drawable.frog))

        recycler.adapter = MedicalAdapter(this, items)

    }
}