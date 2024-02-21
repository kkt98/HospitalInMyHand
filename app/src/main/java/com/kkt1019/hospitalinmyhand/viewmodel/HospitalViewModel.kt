package com.kkt1019.hospitalinmyhand.viewmodel

import android.location.Location
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kkt1019.hospitalinmyhand.data.HospitalItem
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserFactory
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

class HospitalViewModel : ViewModel() {

    private val _hospitalData = MutableLiveData<List<HospitalItem>>()
    val hospitalData: LiveData<List<HospitalItem>> = _hospitalData

    private val _filteredHospitalData = MutableLiveData<List<HospitalItem>>()
    val filteredHospitalData: LiveData<List<HospitalItem>> = _filteredHospitalData

    fun fetchDataFromNetwork() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val address = "http://apis.data.go.kr/B552657/ErmctInfoInqireService/getEgytBassInfoInqire?" +
                        "serviceKey=H7PvoIiO2D6%2BqVfe6kF2WAoJgdpbVUtJT52Wx7dL6%2BDLP4IEk5i5xqP%2BGZMDktix9xaYS03X6YP4JtLGSnuunw%3D%3D" +
                        "&pageNo=1&numOfRows=10000"

                val url = URL(address)
                val conn = url.openConnection() as HttpURLConnection
                conn.doInput = true
                val ips = conn.inputStream
                val isr = InputStreamReader(ips)
                val factory = XmlPullParserFactory.newInstance()
                val xpp = factory.newPullParser()
                xpp.setInput(isr)
                var eventType = xpp.eventType
                val allItems = mutableListOf<HospitalItem>()
                var item: HospitalItem? = null
                while (eventType != XmlPullParser.END_DOCUMENT) {
                    when (eventType) {
                        XmlPullParser.START_TAG -> {
                            val tagName = xpp.name
                            if (tagName == "item") {
                                item = HospitalItem()
                            } else {
                                xpp.next()
                                item?.apply {
                                    when (tagName) {
                                        "dutyAddr" -> dutyAddr = xpp.text
                                        "dutyName" -> dutyName = xpp.text
                                        "dutyTel1" -> dutyTell = xpp.text
                                        "dutyTime1c", "dutyTime1s", "dutyTime2c", "dutyTime2s", "dutyTime3c",
                                        "dutyTime3s", "dutyTime4c", "dutyTime4s", "dutyTime5c", "dutyTime5s",
                                        "dutyTime6c", "dutyTime6s", "wgs84Lat", "wgs84Lon", "hpid", "dgidIdName" -> {
                                            val value = xpp.text
                                            when (tagName) {
                                                "dutyTime1c" -> dutyTime1c = value
                                                "dutyTime1s" -> dutyTime1s = value
                                                "dutyTime2c" -> dutyTime2c = value
                                                "dutyTime2s" -> dutyTime2s = value
                                                "dutyTime3c" -> dutyTime3c = value
                                                "dutyTime3s" -> dutyTime3s = value
                                                "dutyTime4c" -> dutyTime4c = value
                                                "dutyTime4s" -> dutyTime4s = value
                                                "dutyTime5c" -> dutyTime5c = value
                                                "dutyTime5s" -> dutyTime5s = value
                                                "dutyTime6c" -> dutyTime6c = value
                                                "dutyTime6s" -> dutyTime6s = value
                                                "wgs84Lat" -> wgs84Lat = value
                                                "wgs84Lon" -> wgs84Lon = value
                                                "hpid" -> hpid = value
                                                "dgidIdName" -> dgidIdName = value
                                                else -> { }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                        XmlPullParser.END_TAG -> {
                            if (xpp.name == "item") {
                                item?.let { allItems.add(it) }
                            }
                        }
                    }
                    eventType = xpp.next()
                }
                _hospitalData.postValue(allItems)
            } catch (e: Exception) {
                Log.e("HospitalViewModel", "Error fetching hospital data", e)
            }
        }
    }

    fun sortByUserLocation(lat: Double, lon: Double, selectedHospitalType: String?) {

        CoroutineScope(Dispatchers.Default).launch {
            val filteredAndSortedList = _hospitalData.value
                ?.filter { item ->
                    // 병원 종류에 대한 필터링 조건을 여기에 추가
                    // selectedHospitalType이 null이거나 빈 문자열인 경우 모든 병원을 포함
                    // 그렇지 않으면 해당 병원 종류만 포함
                    selectedHospitalType.isNullOrEmpty() || item.dgidIdName == selectedHospitalType
                }
                ?.sortedBy { item ->
                    val itemLocation = Location("").apply {
                        latitude = item.wgs84Lat!!.toDoubleOrNull() ?: 0.0
                        longitude = item.wgs84Lon!!.toDoubleOrNull() ?: 0.0
                    }
                    val userLocation = Location("").apply {
                        latitude = lat
                        longitude = lon
                    }
                    userLocation.distanceTo(itemLocation)
                }
            _filteredHospitalData.postValue(filteredAndSortedList ?: listOf())
        }
    }

    fun filterDataBySelection(city: String?, neighborhood: String?, hospitalType: String?) {

        CoroutineScope(Dispatchers.Default).launch {
            val filteredList = _hospitalData.value?.filter { item ->
                (city == null || item.dutyAddr!!.contains(city)) &&
                        (neighborhood == null || item.dutyAddr!!.contains(neighborhood)) &&
                        (hospitalType == null || item.dgidIdName!!.contains(hospitalType))
            }
            _filteredHospitalData.postValue(filteredList ?: listOf())
        }

    }
}



