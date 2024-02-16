package com.kkt1019.hospitalinmyhand.data

import com.tickaroo.tikxml.annotation.Element
import com.tickaroo.tikxml.annotation.PropertyElement
import com.tickaroo.tikxml.annotation.Xml

@Xml(name = "response")
data class Hospital1Items(
    @Element(name = "body")
    val body: Body,
    @Element(name="header")
    val header: Header
)

@Xml
data class Header(
    @PropertyElement(name="resultCode")
    val resultCode: Int,
    @PropertyElement(name="resultMsg")
    val resultMsg: String
)

@Xml
data class Body(
    @Element(name="items")
    val items: Items,
    @PropertyElement(name="numOfRows")
    val numOfRows: Int,
    @PropertyElement(name="pageNo")
    val pageNo: Int,
    @PropertyElement(name="totalCount")
    val totalCount: Int
)

@Xml
data class Items(
    @Element(name="item")
    val item: List<Item>
)

@Xml
data class Item(
    @PropertyElement(name = "dutyAddr")
    var dutyAddr: String,
    @PropertyElement(name = "dutyName")
    var dutyName: String,
    @PropertyElement(name="dutyTel1")
    var dutyTel1: String,
    @PropertyElement(name = "dutyTime1c")
    var dutyTime1c: String,
    @PropertyElement(name = "dutyTime1s")
    var dutyTime1s: String,
    @PropertyElement(name = "dutyTime2c")
    var dutyTime2c: String,
    @PropertyElement(name = "dutyTime2s")
    var dutyTime2s: String,
    @PropertyElement(name="dutyTime3c")
    var dutyTime3c: String,
    @PropertyElement(name = "dutyTime3s")
    var dutyTime3s: String,
    @PropertyElement(name = "dutyTime4c")
    var dutyTime4c: String,
    @PropertyElement(name="dutyTime4s")
    var dutyTime4s: String,
    @PropertyElement(name = "dutyTime5c")
    var dutyTime5c: String,
    @PropertyElement(name = "dutyTime5s")
    var dutyTime5s: String,
    @PropertyElement(name="dutyTime6c")
    var dutyTime6c: String,
    @PropertyElement(name = "dutyTime6s")
    var dutyTime6s: String,
    @PropertyElement(name = "wgs84Lat")
    var wgs84Lat: String,
    @PropertyElement(name="wgs84Lon")
    var wgs84Lon: String,
    @PropertyElement(name = "hpid")
    var hpid: String,
    @PropertyElement(name="dgidIdName")
    var dgidIdName: String,
)