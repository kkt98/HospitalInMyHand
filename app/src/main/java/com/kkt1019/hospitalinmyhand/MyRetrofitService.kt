package com.kkt1019.hospitalinmyhand

import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.*

interface  MyRetrofitService {

    @GET("/hospital/MyloadDB.php")
    fun loadDataFromServer(@Query("useremail") useremail:String): Call<ArrayList<ItemVO?>>

}