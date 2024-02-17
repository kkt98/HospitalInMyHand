package com.kkt1019.hospitalinmyhand.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kkt1019.hospitalinmyhand.data.HomePage1Item
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserFactory
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

class HospitalViewModel : ViewModel() {

    private val _hospitalData = MutableLiveData<List<HomePage1Item>>()
    val hospitalData: LiveData<List<HomePage1Item>> = _hospitalData

    fun fetchDataFromNetwork() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val address =
                    ("http://apis.data.go.kr/B552657/HsptlAsembySearchService/getHsptlBassInfoInqire?service" +
                            "Key=H7PvoIiO2D6%2BqVfe6kF2WAoJgdpbVUtJT52Wx7dL6%2BDLP4IEk5i5xqP%2BGZMDktix9xaYS03X6YP4JtLGSnuunw%3D%3D" +
                            "&pageNo=1&numOfRows=3000")

                val url = URL(address)
                val conn = url.openConnection() as HttpURLConnection
                conn.doInput = true
                val ips = conn.inputStream
                val isr = InputStreamReader(ips)
                val factory = XmlPullParserFactory.newInstance()
                val xpp = factory.newPullParser()
                xpp.setInput(isr)
                var eventType = xpp.eventType
                val allitems = mutableListOf<HomePage1Item>()
                var item: HomePage1Item? = null

                while (eventType != XmlPullParser.END_DOCUMENT) {
                    when (eventType) {
                        XmlPullParser.START_TAG -> {
                            val tagName = xpp.name
                            when (tagName) {
                                "item" -> item = HomePage1Item()
                                "dutyAddr" -> {
                                    xpp.next()
                                    item?.dutyAddr = xpp.text
                                }
                                "dutyName" -> {
                                    xpp.next()
                                    item?.dutyName = xpp.text
                                }
                                "dutyTel1" -> {
                                    xpp.next()
                                    item?.dutyTell = xpp.text
                                }
                                "dutyTime1c" -> {
                                    xpp.next()
                                    item?.dutyTime1c = xpp.text
                                }
                                "dutyTime1s" -> {
                                    xpp.next()
                                    item?.dutyTime1s = xpp.text
                                }
                                "dutyTime2c" -> {
                                    xpp.next()
                                    item?.dutyTime2c = xpp.text
                                }
                                "dutyTime2s" -> {
                                    xpp.next()
                                    item?.dutyTime2s = xpp.text
                                }
                                "dutyTime3c" -> {
                                    xpp.next()
                                    item?.dutyTime3c = xpp.text
                                }
                                "dutyTime3s" -> {
                                    xpp.next()
                                    item?.dutyTime3s = xpp.text
                                }
                                "dutyTime4c" -> {
                                    xpp.next()
                                    item?.dutyTime4c = xpp.text
                                }
                                "dutyTime4s" -> {
                                    xpp.next()
                                    item?.dutyTime4s = xpp.text
                                }
                                "dutyTime5c" -> {
                                    xpp.next()
                                    item?.dutyTime5c = xpp.text
                                }
                                "dutyTime5s" -> {
                                    xpp.next()
                                    item?.dutyTime5s = xpp.text
                                }
                                "dutyTime6c" -> {
                                    xpp.next()
                                    item?.dutyTime6c = xpp.text
                                }
                                "dutyTime6s" -> {
                                    xpp.next()
                                    item?.dutyTime6s = xpp.text
                                }
                                "wgs84Lat" -> {
                                    xpp.next()
                                    item?.wgs84Lat = xpp.text
                                }
                                "wgs84Lon" -> {
                                    xpp.next()
                                    item?.wgs84Lon = xpp.text
                                }
                                "hpid" -> {
                                    xpp.next()
                                    item?.hpid = xpp.text
                                }
                                "dgidIdName" -> {
                                    xpp.next()
                                    item?.dgidIdName = xpp.text
                                }
                            }
                        }
                        XmlPullParser.END_TAG -> {
                            if (xpp.name == "item") {
                                item?.let { allitems.add(it) }
                            }
                        }
                    }
                    eventType = xpp.next()
                }

                _hospitalData.postValue(allitems)

            } catch (e: Exception) {
                Log.e("HospitalViewModel", "Error fetching hospital data", e)
            }
        }
    }
}
