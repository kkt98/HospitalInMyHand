package com.kkt1019.hospitalinmyhand

data class MedicalItemVO(val body:ItemsBody )

//data class aaa(val resultCod:String)

data class ItemsBody(var items:MutableList<MedicalItems>)

data class MedicalItems(
    val entpName:String,
    val itemName:String,
    val efcyQesitm:String, //효능
    val useMethodQesitm:String, //사용법
    val atpnQesitm:String, //주의사항
    val seQesitm:String, //부작용
    val itemImage:String //낱알 이미지
)

