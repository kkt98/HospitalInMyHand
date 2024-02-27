package com.kkt1019.hospitalinmyhand.viewmodel

import android.location.Location
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kkt1019.hospitalinmyhand.data.HospitalItem
import com.kkt1019.hospitalinmyhand.repository.HospitalRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserFactory
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import java.net.URLEncoder
import java.nio.charset.StandardCharsets
import javax.inject.Inject

class HospitalViewModel @Inject constructor(
    private val repository: HospitalRepository
) : ViewModel() {

    private val _hospitalData = MutableLiveData<List<HospitalItem>>()
    val hospitalData: LiveData<List<HospitalItem>> = _hospitalData

    private val _filteredHospitalData = MutableLiveData<List<HospitalItem>>()
    val filteredHospitalData: LiveData<List<HospitalItem>> = _filteredHospitalData

    fun fetchDataFromNetwork() {
       viewModelScope.launch {
           _hospitalData.postValue(repository.fetchDataFromNetwork())
       }
    }

    fun sortByUserLocation(lat: Double, lon: Double, selectedHospitalType: String?) {
        viewModelScope.launch {
            _hospitalData.value?.let { items ->
                val filteredAndSortedList = repository.sortByUserLocation(items, lat, lon, selectedHospitalType)
                _filteredHospitalData.postValue(filteredAndSortedList)
            }
        }
    }

    fun fetchDataAndFilter(aaa: String, bbb: String, hospitalType: String?) {
        viewModelScope.launch {
            _filteredHospitalData.postValue(repository.fetchDataAndFilter(aaa, bbb, hospitalType))
        }
    }

}



