package com.kkt1019.hospitalinmyhand.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kkt1019.hospitalinmyhand.data.HomePage2Item
import com.kkt1019.hospitalinmyhand.data.HomePage1Item
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserFactory
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

class EmergencyViewModel: ViewModel() {

    private val _emergencyItem = MutableLiveData<List<HomePage2Item>>()
    val emergencyItem : LiveData<List<HomePage2Item>> = _emergencyItem

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
                val allItems = mutableListOf<HomePage2Item>()

                var item: HomePage2Item? = null

                while (eventType != XmlPullParser.END_DOCUMENT) {
                    when (eventType) {
                        XmlPullParser.START_TAG -> {
                            val tagName = xpp.name
                            when (tagName) {
                                "item" -> item = HomePage2Item()
                                "dutyAddr", "dutyName", "dutyTel1", "dutyTel3", "wgs84Lat", "wgs84Lon", "hpid" -> {
                                    xpp.next()
                                    item?.apply {
                                        when (tagName) {
                                            "dutyAddr" -> dutyAddr = xpp.text
                                            "dutyName" -> dutyName = xpp.text
                                            "dutyTel1" -> dutyTel1 = xpp.text
                                            "dutyTel3" -> dutyTel3 = xpp.text
                                            "wgs84Lat" -> wgs84Lat = xpp.text
                                            "wgs84Lon" -> wgs84Lon = xpp.text
                                            "hpid" -> hpid = xpp.text
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

                _emergencyItem.postValue(allItems)

            } catch (e: Exception) {
                Log.e("HospitalViewModel", "Error fetching hospital data", e)
            }
        }
    }
}


//H7PvoIiO2D6%2BqVfe6kF2WAoJgdpbVUtJT52Wx7dL6%2BDLP4IEk5i5xqP%2BGZMDktix9xaYS03X6YP4JtLGSnuunw%3D%3D