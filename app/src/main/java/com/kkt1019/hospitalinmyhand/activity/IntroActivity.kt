package com.kkt1019.hospitalinmyhand.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.kkt1019.hospitalinmyhand.R

class IntroActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_intro)

        Handler(Looper.getMainLooper()).postDelayed({
            startActivity( Intent(this, LoginActivity::class.java) )
            finish()
        } , 1500)
    }

    override fun onPause() {
        super.onPause()
        finish()
    }


}