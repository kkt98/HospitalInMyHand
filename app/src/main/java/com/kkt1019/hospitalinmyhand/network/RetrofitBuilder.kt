package com.kkt1019.hospitalinmyhand.network

import com.tickaroo.tikxml.TikXml
import com.tickaroo.tikxml.retrofit.TikXmlConverterFactory
import retrofit2.Retrofit

object RetrofitBuilder {

    var parser = TikXml.Builder().exceptionOnUnreadXml(false).build()


    val retrofit = Retrofit.Builder()
        .baseUrl("http://apis.data.go.kr/")
        .addConverterFactory(TikXmlConverterFactory.create(parser))
        .build()

    val apiService = retrofit.create(ApiService::class.java)

}