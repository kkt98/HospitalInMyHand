package com.kkt1019.hospitalinmyhand.viewmodel

import android.annotation.SuppressLint
import android.app.Application
import android.location.Location
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LocationViewModel @Inject constructor(
    application: Application
) : AndroidViewModel(application) {

    private val _fusedLocationProviderClient: FusedLocationProviderClient =
        LocationServices.getFusedLocationProviderClient(application)

    private val _locationData = MutableLiveData<Location?>()
    val locationData: MutableLiveData<Location?> = _locationData

    @SuppressLint("MissingPermission")
    fun fetchCurrentLocation() {
        _fusedLocationProviderClient.getCurrentLocation(Priority.PRIORITY_HIGH_ACCURACY, null)
            .addOnSuccessListener { location: Location? ->
                _locationData.value = location
            }
    }
}