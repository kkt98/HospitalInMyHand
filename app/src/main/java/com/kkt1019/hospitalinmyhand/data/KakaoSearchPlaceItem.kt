package com.kkt1019.hospitalinmyhand.data

data class KakaoSearchPlaceItemVO(var meta: PlaceMeta, var documents:MutableList<Place>)

data class PlaceMeta(
    var total_count:Int,
    var pageable_count:Int,
    var is_end:Boolean
)

data class Place(
    var id:String,
    var place_name:String,
    var category_name:String,
    var phone:String,
    var address_name:String,
    var road_address_name:String,
    var x:String, //lon
    var y:String, //lat
    var place_url:String,
    var distance:String
)
