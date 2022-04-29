package com.kkt1019.hospitalinmyhand


import com.google.gson.annotations.SerializedName

data class EmgMedResponse(
    @SerializedName("body")
    val body: Body?,
    @SerializedName("header")
    val header: Header?
)

data class Body(
    @SerializedName("items")
    val items: List<Item>?,
    @SerializedName("numOfRows")
    val numOfRows: Int?,
    @SerializedName("pageNo")
    val pageNo: Int?,
    @SerializedName("totalCount")
    val totalCount: Int?
)

data class Header(
    @SerializedName("resultCode")
    val resultCode: String?,
    @SerializedName("resultMsg")
    val resultMsg: String?
)

data class Item(
    @SerializedName("atpnQesitm")
    val atpnQesitm: String?,
    @SerializedName("atpnWarnQesitm")
    val atpnWarnQesitm: Any?,
    @SerializedName("depositMethodQesitm")
    val depositMethodQesitm: String?,
    @SerializedName("efcyQesitm")
    val efcyQesitm: String?,
    @SerializedName("entpName")
    val entpName: String?,
    @SerializedName("intrcQesitm")
    val intrcQesitm: Any?,
    @SerializedName("itemImage")
    val itemImage: Any?,
    @SerializedName("itemName")
    val itemName: String?,
    @SerializedName("itemSeq")
    val itemSeq: String?,
    @SerializedName("openDe")
    val openDe: String?,
    @SerializedName("seQesitm")
    val seQesitm: Any?,
    @SerializedName("updateDe")
    val updateDe: String?,
    @SerializedName("useMethodQesitm")
    val useMethodQesitm: String?
)