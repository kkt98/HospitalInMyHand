package com.kkt1019.hospitalinmyhand.repository

import android.location.Location
import android.util.Log
import com.kkt1019.hospitalinmyhand.data.EmergencyItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserFactory
import java.net.HttpURLConnection
import java.net.URL
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class EmergencyRepository @Inject constructor()  {

    suspend fun fetchDataFromNetwork(): List<EmergencyItem> = withContext(Dispatchers.IO) {
        val address = ("http://apis.data.go.kr/B552657/ErmctInfoInqireService/getEgytBassInfoInqire?" +
                "serviceKey=H7PvoIiO2D6%2BqVfe6kF2WAoJgdpbVUtJT52Wx7dL6%2BDLP4IEk5i5xqP%2BGZMDktix9xaYS03X6YP4JtLGSnuunw%3D%3D" +
                "&pageNo=1&numOfRows=10000")

        try {
            val url = URL(address)
            val conn = url.openConnection() as HttpURLConnection
            conn.doInput = true

            val ips = conn.inputStream
            val isr = ips.reader()

            val factory = XmlPullParserFactory.newInstance()
            val xpp = factory.newPullParser()
            xpp.setInput(isr)

            var eventType = xpp.eventType
            val items = mutableListOf<EmergencyItem>()

            var item: EmergencyItem? = null

            while (eventType != XmlPullParser.END_DOCUMENT) {
                when (eventType) {
                    XmlPullParser.START_TAG -> {
                        val tagName = xpp.name
                        if (tagName == "item") item = EmergencyItem()
                        if (item != null) {
                            if (tagName in listOf("dutyAddr", "dutyName", "dutyTel1", "dutyTel3", "wgs84Lat", "wgs84Lon", "hpid")) {
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
                    }
                    XmlPullParser.END_TAG -> {
                        if (xpp.name == "item" && !item?.dutyTel3.isNullOrEmpty()) {
                            item?.let { items.add(it) }
                            item = null
                        }
                    }
                }
                eventType = xpp.next()
            }

            items
        } catch (e: Exception) {
            Log.e("EmergencyRepository", "Error fetching emergency data", e)
            emptyList()
        }
    }

    fun sortByUserLocation(items: List<EmergencyItem>, lat: Double, lon: Double): List<EmergencyItem> {
        return items.sortedBy { item ->
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
    }

    fun filterDataBySelection(items: List<EmergencyItem>, city: String?, neighborhood: String?): List<EmergencyItem> {
        return items.filter { item ->
            (city == null || item.dutyAddr.contains(city)) &&
                    (neighborhood == null || item.dutyAddr.contains(neighborhood))
        }
    }
}