package com.kkt1019.hospitalinmyhand.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.google.android.gms.location.*
import com.kkt1019.hospitalinmyhand.Permission
import com.kkt1019.hospitalinmyhand.R
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.kkt1019.hospitalinmyhand.data.ShareData
import com.kkt1019.hospitalinmyhand.databinding.ActivityMainBinding
import com.kkt1019.hospitalinmyhand.viewmodel.LocationViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding
    private val locationViewModel: LocationViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.activity = this

        Glide.with(this).load(R.drawable.mmedical).into(binding.gifHospital)
        Glide.with(this).load(R.drawable.map).into(binding.gifMap)
        Glide.with(this).load(R.drawable.medical).into(binding.gifMedical)
        Glide.with(this).load(R.drawable.favorit).into(binding.gifFav)
        Glide.with(this).load(R.drawable.hos).into(binding.gifPharmacy)
        Glide.with(this).load(R.drawable.calendar).into(binding.gifCalender)

        Permission.requestPermission()

        locationViewModel.fetchCurrentLocation()
        locationViewModel.locationData.observe(this, Observer {
            ShareData.lat = it?.latitude ?: 0.0
            ShareData.lng = it?.longitude ?: 0.0
        })
    }

    fun onClickHospital() {
        val intent = Intent(this, HospitalActivity::class.java)
        startActivity(intent)
    }

    fun onClickMedical() {
        val intent = Intent(this, MedicalActivity::class.java)
        startActivity(intent)
    }

    fun onClickMap() {
        val intent = Intent(this, MapActivity::class.java)
        startActivity(intent)
    }

    fun onClickPharmacy() {
        val intent = Intent(this, PharmacyActivity::class.java)
        startActivity(intent)
    }

    fun onClickFavorit() {
        val intent = Intent(this, FavoritActivity::class.java)
        startActivity(intent)
    }

    fun onClickCalender() {
        val intent = Intent(this, CalendarActivity::class.java)
        startActivity(intent)
    }
}