package com.kkt1019.hospitalinmyhand

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.kkt1019.hospitalinmyhand.databinding.ActivityFavoritBinding

class FavoritActivity : AppCompatActivity() {
    val binding: ActivityFavoritBinding by lazy { ActivityFavoritBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }
}