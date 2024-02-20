package com.kkt1019.hospitalinmyhand.network

import com.kkt1019.hospitalinmyhand.data.KakaoSearchPlaceItemVO
import com.kkt1019.hospitalinmyhand.data.MedicalItemVO
import retrofit2.Call
import retrofit2.http.*

interface RetrofitService {

    @GET("/1471000/DrbEasyDrugInfoService/getDrbEasyDrugList")
    fun MedicalData(@Query("ServiceKey") serviceKey:String, @Query("itemName") itemName:String, @Query("type") type:String):Call<MedicalItemVO>

    @Headers("Authorization: KakaoAK 786a8f8eb066b98df93f703ee0e7615e")
    @GET("/v2/local/search/keyword.json")
    fun searchPlaces(@Query("query")query:String, @Query("x")longitude:String, @Query("y")latitude:String):Call<KakaoSearchPlaceItemVO>

}

