package com.kkt1019.hospitalinmyhand.viewmodel

import android.location.Location
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kkt1019.hospitalinmyhand.data.EmergencyItem
import com.kkt1019.hospitalinmyhand.data.HospitalItem
import com.kkt1019.hospitalinmyhand.repository.EmergencyRepository
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
class EmergencyViewModel @Inject constructor(private val repository: EmergencyRepository) : ViewModel() {

    private val _emergencyItem = MutableLiveData<List<EmergencyItem>>()
    val emergencyItem: LiveData<List<EmergencyItem>> = _emergencyItem

    private val _filteredEmergencyData = MutableLiveData<List<EmergencyItem>>()
    val filteredEmergencyData: LiveData<List<EmergencyItem>> = _filteredEmergencyData

    fun fetchDataFromNetwork() {
        viewModelScope.launch {
            try {
                val items = repository.fetchDataFromNetwork()
                _emergencyItem.postValue(items)
            } catch (e: Exception) {
                Log.e("EmergencyViewModel", "Error fetching emergency data", e)
            }
        }
    }

    fun sortByUserLocation(lat: Double, lon: Double) {
        _emergencyItem.value?.let { items ->
            viewModelScope.launch {
                val sortedItems = repository.sortByUserLocation(items, lat, lon)
                _filteredEmergencyData.postValue(sortedItems)
            }
        }
    }

    fun filterDataBySelection(city: String?, neighborhood: String?) {
        _emergencyItem.value?.let { items ->
            viewModelScope.launch {
                val filteredItems = repository.filterDataBySelection(items, city, neighborhood)
                _filteredEmergencyData.postValue(filteredItems)
            }
        }
    }
}


