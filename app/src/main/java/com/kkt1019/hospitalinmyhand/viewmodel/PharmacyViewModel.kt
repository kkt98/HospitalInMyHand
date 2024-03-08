package com.kkt1019.hospitalinmyhand.viewmodel

import android.location.Location
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kkt1019.hospitalinmyhand.data.PharmacyItem
import com.kkt1019.hospitalinmyhand.repository.PharmacyRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserFactory
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import javax.inject.Inject

@HiltViewModel
class PharmacyViewModel @Inject constructor(
    private val repository: PharmacyRepository
) : ViewModel() {

    private val _pharmacyData = MutableLiveData<List<PharmacyItem>>()
    val pharmacyData: LiveData<List<PharmacyItem>> = _pharmacyData

    private val _filteredPharmacyData = MutableLiveData<List<PharmacyItem>>()
    val filteredPharmacyData: LiveData<List<PharmacyItem>> = _filteredPharmacyData
    fun fetchPharmacyDataNetwork() {
       viewModelScope.launch {
           _pharmacyData.postValue(repository.fetchPharmacyDataNetwork())
       }
    }

    fun sortByUserLocation(lat: Double, lon: Double) {
        _pharmacyData.value?.let { pharmacyItems ->
            viewModelScope.launch {
                _filteredPharmacyData.postValue(repository.sortByUserLocation(pharmacyItems, lat, lon))
            }
        }
    }

    fun filterDataBySelection(city: String?, neighborhood: String?) {
        _pharmacyData.value.let { pharmacyItems ->
            viewModelScope.launch {
                _filteredPharmacyData.postValue(repository.filterDataBySelection(pharmacyItems!!, city, neighborhood))
            }

        }

    }

}