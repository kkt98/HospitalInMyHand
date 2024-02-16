package com.kkt1019.hospitalinmyhand.network

import com.kkt1019.hospitalinmyhand.HomePage1Item
import com.kkt1019.hospitalinmyhand.data.Hospital1Items
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("B552657/HsptlAsembySearchService/getHsptlBassInfoInqire")
    fun getHospitalInfo(
        @Query("serviceKey") serviceKey: String,
        @Query("pageNo") pageNo: Int,
        @Query("numOfRows") numOfRows: Int
    ): Call<Hospital1Items>
}