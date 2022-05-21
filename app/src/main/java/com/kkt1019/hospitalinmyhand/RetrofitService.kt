package com.kkt1019.hospitalinmyhand

import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.*

public interface RetrofitService {

    @Multipart
    @POST("/hospital/insert.php")
    fun postDataToServer(@PartMap dataPart: Map<String, String>,
                         @Part filePart: MultipartBody.Part?
    ): Call<String?>?

    @GET("/hospital/loadDB.php")
    fun loadDataFromServer(@Query("uniqueid") uniqueid:String): Call<ArrayList<ItemVO?>>

    @GET("/hospital/Delete.php")
    fun DeleteDataFromServer(@Query("nickname") nickname:String): Call<ArrayList<ItemVO?>>

}