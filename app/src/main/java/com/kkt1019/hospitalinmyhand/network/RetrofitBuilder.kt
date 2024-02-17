package com.kkt1019.hospitalinmyhand.network

import retrofit2.Retrofit
import retrofit2.converter.simplexml.SimpleXmlConverterFactory

object RetrofitBuilder {


    val retrofit = Retrofit.Builder()
        .baseUrl("http://apis.data.go.kr/")
        .addConverterFactory(SimpleXmlConverterFactory.create())
        .build()

    val apiService = retrofit.create(ApiService::class.java)

}