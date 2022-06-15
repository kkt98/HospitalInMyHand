package com.kkt1019.hospitalinmyhand

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

class RetrofitHelper {

    companion object{

        fun getRetrofitInstanceGson(): Retrofit? {
            val builder = Retrofit.Builder()
            builder.baseUrl("http://sux1011.dothome.co.kr")
            builder.addConverterFactory(GsonConverterFactory.create())
            return builder.build()
        }

        fun getRetrofitInstanceGson2(): Retrofit? {
            val builder = Retrofit.Builder()
            builder.baseUrl("http://sux1011.dothome.co.kr")
            builder.addConverterFactory(GsonConverterFactory.create())
            return builder.build()
        }

        fun getRetrofitInstanceScalars(): Retrofit? {
            return Retrofit.Builder().baseUrl("http://sux1011.dothome.co.kr").addConverterFactory(ScalarsConverterFactory.create()).build()
        }

        fun calendarRetrofit(): Retrofit {
            return Retrofit.Builder().baseUrl("http://sux1011.dothome.co.kr").addConverterFactory(ScalarsConverterFactory.create()).build()
        }

        val gson : Gson = GsonBuilder()
            .setLenient()
            .create()

        fun getRetrofitInstance() : Retrofit{
            val retrofit: Retrofit = Retrofit.Builder()
                .baseUrl("http://apis.data.go.kr/")
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()

            return retrofit
        }

        fun getRetrofitInstance2() : Retrofit{
            val retrofit: Retrofit = Retrofit.Builder()
                .baseUrl("http://apis.data.go.kr/")
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()

            return retrofit
        }

    }


}