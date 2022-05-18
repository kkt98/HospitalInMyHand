package com.kkt1019.hospitalinmyhand

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.kkt1019.hospitalinmyhand.databinding.ActivityCalendarBinding

class CalendarActivity : AppCompatActivity() {

    val binding:ActivityCalendarBinding by lazy { ActivityCalendarBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)


    }
}