package com.kkt1019.hospitalinmyhand

import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.PartMap

public interface RetrofitService {

    @Multipart
    @POST("/hospital/insert.php")
    fun postDataToServer(@PartMap dataPart: Map<String, String>,
                         @Part filePart: MultipartBody.Part?
    ): Call<String?>?

}