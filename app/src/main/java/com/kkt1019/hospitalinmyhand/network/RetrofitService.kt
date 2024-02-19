package com.kkt1019.hospitalinmyhand.network

import com.kkt1019.hospitalinmyhand.CalendarItemVO
import com.kkt1019.hospitalinmyhand.ItemVO
import com.kkt1019.hospitalinmyhand.KakaoSearchPlaceItemVO
import com.kkt1019.hospitalinmyhand.MedicalItemVO
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
    fun MedicalData(@Query("ServiceKey") serviceKey:String, @Query("itemName") itemName:String, @Query("type") type:String):Call<MedicalItemVO>

    @Multipart
    @POST("/hospital/insertDB_cal.php")
    fun calendarinsert(@PartMap date: Map<String, String>):Call<String>

    @GET("/hospital/loadDB_cal.php")
    fun calendarLoad(@Query("email") email:String, @Query("date") date:String): Call<ArrayList<CalendarItemVO>>

    @Headers("Authorization: KakaoAK 786a8f8eb066b98df93f703ee0e7615e")
    @GET("/v2/local/search/keyword.json")
    fun searchPlaces(@Query("query")query:String, @Query("x")longitude:String, @Query("y")latitude:String):Call<KakaoSearchPlaceItemVO>

    @Headers("Authorization: KakaoAK 786a8f8eb066b98df93f703ee0e7615e")
    @GET("/v2/local/search/keyword.json")
    fun searchPlacesToString(@Query("query")query:String, @Query("x")longitude:String, @Query("y")latitude:String):Call<String>

}

