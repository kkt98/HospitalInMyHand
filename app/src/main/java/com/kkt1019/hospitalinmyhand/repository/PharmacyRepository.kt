package com.kkt1019.hospitalinmyhand.repository

import android.location.Location
import android.util.Log
import com.kkt1019.hospitalinmyhand.data.PharmacyItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserFactory
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import javax.inject.Inject
import javax.inject.Singleton

class PharmacyRepository @Inject constructor() {

    suspend fun fetchPharmacyDataNetwork() : List<PharmacyItem> = withContext(Dispatchers.IO) {
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
            return@withContext allItems
        } catch (e: Exception) {
            Log.e("PharmacyViewModel", "Error fetching pharmacy data", e)

            return@withContext emptyList()
        }
    }

    fun sortByUserLocation(items: List<PharmacyItem>, lat: Double, lon: Double) : List<PharmacyItem>{
        return items.sortedBy { item ->
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
    }

    fun filterDataBySelection(items: List<PharmacyItem>, city: String?, neighborhood: String?) : List<PharmacyItem> {
        return items.filter { item ->
            (city == null || item.addr.contains(city)) &&
                    (neighborhood == null || item.addr.contains(neighborhood))
        }
    }

}