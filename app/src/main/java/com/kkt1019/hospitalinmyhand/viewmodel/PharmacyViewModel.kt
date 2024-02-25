package com.kkt1019.hospitalinmyhand.viewmodel

import android.location.Location
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kkt1019.hospitalinmyhand.data.PharmacyItem
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserFactory
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

class PharmacyViewModel: ViewModel() {

    private val _pharmacyData = MutableLiveData<List<PharmacyItem>>()
    val pharmacyData: LiveData<List<PharmacyItem>> = _pharmacyData

    private val _filteredPharmacyData = MutableLiveData<List<PharmacyItem>>()
    val filteredPharmacyData: LiveData<List<PharmacyItem>> = _filteredPharmacyData
    fun fetchPharmacyDataNetwork() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val address = ("http://apis.data.go.kr/B551182/pharmacyInfoService/getParmacyBasisList" +
                        "?serviceKey=H7PvoIiO2D6%2BqVfe6kF2WAoJgdpbVUtJT52Wx7dL6%2BDLP4IEk5i5xqP%2BGZMDktix9xaYS03X6YP4JtLGSnuunw%3D%3D" +
                        "&pageNo=1" +
                        "&numOfRows=10000")

                val url = URL(address)
                val conn = url.openConnection() as HttpURLConnection
                conn.doInput = true

                val ips = conn.inputStream
                val isr = InputStreamReader(ips)

                val factory = XmlPullParserFactory.newInstance()
                val xpp = factory.newPullParser()
                xpp.setInput(isr)

                var eventType = xpp.eventType
                val allItems = mutableListOf<PharmacyItem>()

                var item: PharmacyItem? = null

                while (eventType != XmlPullParser.END_DOCUMENT) {
                    when (eventType) {
                        XmlPullParser.START_TAG -> {
                            val tagName = xpp.name
                            if (tagName == "item") item = PharmacyItem()
                            if (item != null && tagName in listOf("addr", "yadmNm", "telno", "XPos", "YPos", "ykiho", "distance")) {
                                xpp.next()
                                when (tagName) {
                                    "addr" -> item.addr = xpp.text
                                    "yadmNm" -> item.yadmNm = xpp.text
                                    "telno" -> item.telno = xpp.text
                                    "XPos" -> item.xPos = xpp.text
                                    "YPos" -> item.yPos = xpp.text
                                    "ykiho" -> item.ykiho = xpp.text
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
                _pharmacyData.postValue(allItems)
            } catch (e: Exception) {
                Log.e("PharmacyViewModel", "Error fetching pharmacy data", e)
            }
        }
    }

    fun sortByUserLocation(lat: Double, lon: Double) {

        CoroutineScope(Dispatchers.Default).launch {
            val filteredAndSortedList = _pharmacyData.value
                ?.sortedBy { item ->
                    val itemLocation = Location("").apply {
                        latitude = item.yPos.toDoubleOrNull() ?: 0.0
                        longitude = item.xPos.toDoubleOrNull() ?: 0.0
                    }
                    val userLocation = Location("").apply {
                        latitude = lat
                        longitude = lon
                    }
                    userLocation.distanceTo(itemLocation)
                }
            _filteredPharmacyData.postValue(filteredAndSortedList ?: listOf())
        }
    }

    fun filterDataBySelection(city: String?, neighborhood: String?) {

        CoroutineScope(Dispatchers.Default).launch {
            val filteredList = _pharmacyData.value?.filter { item ->
                (city == null || item.addr.contains(city)) &&
                        (neighborhood == null || item.addr.contains(neighborhood))
            }
            _filteredPharmacyData.postValue(filteredList ?: listOf())
        }

    }

}