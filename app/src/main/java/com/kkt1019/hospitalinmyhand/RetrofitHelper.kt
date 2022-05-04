package com.kkt1019.hospitalinmyhand

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

public class RetrofitHelper {

    companion object{

        fun getRetrofitInstanceGson(): Retrofit? {
            val builder = Retrofit.Builder()
            builder.baseUrl("http://sux1011.dothome.co.kr")
            builder.addConverterFactory(GsonConverterFactory.create())
            return builder.build()
        }

        fun getRetrofitInstanceScalars(): Retrofit? {
            return Retrofit.Builder().baseUrl("http://sux1011.dothome.co.kr").addConverterFactory(ScalarsConverterFactory.create()).build()
        }

    }


}