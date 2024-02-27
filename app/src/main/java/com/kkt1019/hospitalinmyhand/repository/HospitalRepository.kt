package com.kkt1019.hospitalinmyhand.repository

import android.location.Location
import android.util.Log
import com.kkt1019.hospitalinmyhand.data.HospitalItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserFactory
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import java.net.URLEncoder
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class HospitalRepository @Inject constructor() {

    suspend fun fetchDataFromNetwork(): List<HospitalItem> = withContext(Dispatchers.IO) {
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
           allItems
        } catch (e: Exception) {
            Log.e("HospitalViewModel", "Error fetching hospital data", e)
            emptyList()
        }
    }

    private suspend fun fetchDataFromNetwork2(aaa: String, bbb: String): List<HospitalItem> = withContext(Dispatchers.IO) {
        try {
            val address = if (bbb != null) {
                "http://apis.data.go.kr/B552657/HsptlAsembySearchService/getHsptlMdcncListInfoInqire?" +
                        "serviceKey=H7PvoIiO2D6%2BqVfe6kF2WAoJgdpbVUtJT52Wx7dL6%2BDLP4IEk5i5xqP%2BGZMDktix9xaYS03X6YP4JtLGSnuunw%3D%3D" +
                        "&Q0=${URLEncoder.encode(aaa, "UTF-8")}&Q1=${URLEncoder.encode(bbb, "UTF-8")}" +
                        "&pageNo=1&numOfRows=1000"
            } else{
                "http://apis.data.go.kr/B552657/HsptlAsembySearchService/getHsptlMdcncListInfoInqire?" +
                        "serviceKey=H7PvoIiO2D6%2BqVfe6kF2WAoJgdpbVUtJT52Wx7dL6%2BDLP4IEk5i5xqP%2BGZMDktix9xaYS03X6YP4JtLGSnuunw%3D%3D" +
                        "&Q0=${URLEncoder.encode(aaa, "UTF-8")}" +
                        "&pageNo=1&numOfRows=1000"
            }


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
            allItems // 파싱된 결과를 반환
        }catch (e: Exception) {
            Log.e("HospitalViewModel", "Error fetching hospital data", e)
            emptyList()
        }
    }

    fun sortByUserLocation(items: List<HospitalItem>, lat: Double, lon: Double, selectedHospitalType: String?): List<HospitalItem> {
        return items.filter { item ->
            // 병원 종류에 대한 필터링 조건
            selectedHospitalType.isNullOrEmpty() || item.dgidIdName == selectedHospitalType
        }.sortedWith(compareBy { item ->
            // 사용자 위치와 병원 위치 간의 거리 계산
            val itemLocation = Location("").apply {
                latitude = item.wgs84Lat?.toDoubleOrNull() ?: 0.0
                longitude = item.wgs84Lon?.toDoubleOrNull() ?: 0.0
            }
            val userLocation = Location("").apply {
                latitude = lat
                longitude = lon
            }
            userLocation.distanceTo(itemLocation)
        })
    }

    suspend fun fetchDataAndFilter(aaa: String, bbb: String, hospitalType: String?): List<HospitalItem> {
        return withContext(Dispatchers.IO) {
            try {
                // fetchDataFromNetwork2의 결과를 기다립니다.
                val items = fetchDataFromNetwork2(aaa, bbb)

                // 필터링 작업을 수행합니다.
                val filteredItems = if (!hospitalType.isNullOrEmpty()) {
                    items.filter { it.dutyName!!.contains(hospitalType) }
                } else {
                    items // hospitalType이 null이거나 비어 있으면 모든 아이템을 반환
                }

                filteredItems
            } catch (e: Exception) {
                Log.e("HospitalRepository", "Error in fetchDataAndFilter", e)
                emptyList()
            }
        }
    }

}