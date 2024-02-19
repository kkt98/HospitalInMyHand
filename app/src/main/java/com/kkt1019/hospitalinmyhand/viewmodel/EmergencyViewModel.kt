package com.kkt1019.hospitalinmyhand.viewmodel

import android.location.Location
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kkt1019.hospitalinmyhand.data.EmergencyItem
import com.kkt1019.hospitalinmyhand.data.HospitalItem
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserFactory
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

class EmergencyViewModel: ViewModel() {

    private val _emergencyItem = MutableLiveData<List<EmergencyItem>>()
    val emergencyItem : LiveData<List<EmergencyItem>> = _emergencyItem

    private val _filteredEmergencyData = MutableLiveData<List<EmergencyItem>>()
    val filteredEmergencyData: LiveData<List<EmergencyItem>> = _filteredEmergencyData

    fun fetchDataFromNetwork() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val address = ("http://apis.data.go.kr/B552657/ErmctInfoInqireService/getEgytBassInfoInqire?" +
                        "serviceKey=H7PvoIiO2D6%2BqVfe6kF2WAoJgdpbVUtJT52Wx7dL6%2BDLP4IEk5i5xqP%2BGZMDktix9xaYS03X6YP4JtLGSnuunw%3D%3D" +
                        "&pageNo=1&numOfRows=10000")

                val url = URL(address)
                val conn = url.openConnection() as HttpURLConnection
                conn.doInput = true

                val ips = conn.inputStream
                val isr = InputStreamReader(ips)

                val factory = XmlPullParserFactory.newInstance()
                val xpp = factory.newPullParser()
                xpp.setInput(isr)

                var eventType = xpp.eventType
                val allItems = mutableListOf<EmergencyItem>()

                var item: EmergencyItem? = null

                while (eventType != XmlPullParser.END_DOCUMENT) {
                    when (eventType) {
                        XmlPullParser.START_TAG -> {
                            val tagName = xpp.name
                            if (tagName == "item") item = EmergencyItem()
                            if (item != null && tagName in listOf("dutyAddr", "dutyName", "dutyTel1", "dutyTel3", "wgs84Lat", "wgs84Lon", "hpid")) {
                                xpp.next()
                                when (tagName) {
                                    "dutyAddr" -> item.dutyAddr = xpp.text
                                    "dutyName" -> item.dutyName = xpp.text
                                    "dutyTel1" -> item.dutyTel1 = xpp.text
                                    "dutyTel3" -> item.dutyTel3 = xpp.text
                                    "wgs84Lat" -> item.wgs84Lat = xpp.text
                                    "wgs84Lon" -> item.wgs84Lon = xpp.text
                                    "hpid" -> item.hpid = xpp.text
                                }
                            }
                        }
                        XmlPullParser.END_TAG -> {
                            //dutyTel3 가 있는것만 추가
                            if (xpp.name == "item" && !item?.dutyTel3.isNullOrEmpty()) {
                                item?.let { allItems.add(it) }
                                item = null
                            }
                        }
                    }
                    eventType = xpp.next()
                }

                _emergencyItem.postValue(allItems)

            } catch (e: Exception) {
                Log.e("EmergencyViewModel", "Error fetching emergency data", e)
            }
        }
    }

    fun sortByUserLocation(lat: Double, lon: Double) {

        CoroutineScope(Dispatchers.Default).launch {
            val filteredAndSortedList = _emergencyItem.value
                ?.sortedBy { item ->
                    val itemLocation = Location("").apply {
                        latitude = item.wgs84Lat.toDoubleOrNull() ?: 0.0
                        longitude = item.wgs84Lon.toDoubleOrNull() ?: 0.0
                    }
                    val userLocation = Location("").apply {
                        latitude = lat
                        longitude = lon
                    }
                    userLocation.distanceTo(itemLocation)
                }
            _filteredEmergencyData.postValue(filteredAndSortedList ?: listOf())
        }
    }

    fun filterDataBySelection(city: String?, neighborhood: String?) {

        CoroutineScope(Dispatchers.Default).launch {
            val filteredList = _emergencyItem.value?.filter { item ->
                (city == null || item.dutyAddr.contains(city)) &&
                        (neighborhood == null || item.dutyAddr.contains(neighborhood))
            }
            _filteredEmergencyData.postValue(filteredList ?: listOf())
        }

    }
}


