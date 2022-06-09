package com.kkt1019.hospitalinmyhand

import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.*

interface RetrofitService {

    @Multipart
    @POST("/hospital/insert.php")
    fun postDataToServer(@PartMap dataPart: Map<String, String>,
                         @Part filePart: MultipartBody.Part?
    ): Call<String?>?

    @GET("/hospital/loadDB.php")
    fun loadDataFromServer(@Query("uniqueid") uniqueid:String): Call<ArrayList<ItemVO?>>

    @GET("/hospital/Delete.php")
    fun DeleteDataFromServer(@Query("nickname") nickname:String): Call<ArrayList<ItemVO?>>

    @GET("/1471000/DrbEasyDrugInfoService/getDrbEasyDrugList")
    fun MedicalData(@Query("ServiceKey") serviceKey:String, @Query("pageNo") pageNo:Int, @Query("numOfRows") numOfRows:Int, @Query("type") type:String):Call<MedicalItemVO>

    @GET("1471000/DrbEasyDrugInfoService/getDrbEasyDrugList?serviceKey=H7PvoIiO2D6+qVfe6kF2WAoJgdpbVUtJT52Wx7dL6+DLP4IEk5i5xqP+GZMDktix9xaYS03X6YP4JtLGSnuunw==&pageNo=1&numOfRows=3&type=json")
    fun aaa() :Call<MedicalItemVO>

    @GET("/1471000/DrbEasyDrugInfoService/getDrbEasyDrugList?serviceKey=H7PvoIiO2D6+qVfe6kF2WAoJgdpbVUtJT52Wx7dL6+DLP4IEk5i5xqP+GZMDktix9xaYS03X6YP4JtLGSnuunw==")
    fun bbb(@Query("pageNo") pageNo:Int, @Query("numOfRows") numOfRows:Int, @Query("type") type:String):Call<MedicalItemVO>


    @GET("/1471000/DrbEasyDrugInfoService/getDrbEasyDrugList")
    fun MedicalDataString(@Query("ServiceKey") ServiceKey:String, @Query("pageNo") pageNo:Int, @Query("numOfRows") numOfRows:Int, @Query("type") type:String):Call<String>
//
//    @GET("1471000/DrbEasyDrugInfoService/getDrbEasyDrugList?ServiceKey=H7PvoIiO2D6%2BqVfe6kF2WAoJgdpbVUtJT52Wx7dL6%2BDLP4IEk5i5xqP%2BGZMDktix9xaYS03X6YP4JtLGSnuunw%3D%3D&pageNo=1&numOfRows=3&type=json")
//    fun aaa() :Call<String>
//
//    @GET("1471000/DrbEasyDrugInfoService/getDrbEasyDrugList?ServiceKey=H7PvoIiO2D6%2BqVfe6kF2WAoJgdpbVUtJT52Wx7dL6%2BDLP4IEk5i5xqP%2BGZMDktix9xaYS03X6YP4JtLGSnuunw%3D%3D")
//    fun bbb( @Query("pageNo") pageNo:Int, @Query("numOfRows") numOfRows:Int, @Query("type") type:String):Call<String>
}

//1471000/DrbEasyDrugInfoService/getDrbEasyDrugList?ServiceKey=H7PvoIiO2D6%2BqVfe6kF2WAoJgdpbVUtJT52Wx7dL6%2BDLP4IEk5i5xqP%2BGZMDktix9xaYS03X6YP4JtLGSnuunw%3D%3D&pageNo=1&numOfRows=3&type=json