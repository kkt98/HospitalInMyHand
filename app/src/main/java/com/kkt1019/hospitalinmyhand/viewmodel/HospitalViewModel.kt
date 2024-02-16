package com.kkt1019.hospitalinmyhand.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kkt1019.hospitalinmyhand.data.Hospital1Items
import com.kkt1019.hospitalinmyhand.network.RetrofitBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HospitalViewModel: ViewModel() {

    private val _hospitalInfo = MutableLiveData<Hospital1Items>()
    val hospitalInfo: LiveData<Hospital1Items> = _hospitalInfo

    fun fetchHospitalInfo(serviceKey: String, pageNo: Int, numOfRows: Int) {
        RetrofitBuilder.apiService.getHospitalInfo(serviceKey, pageNo, numOfRows).enqueue(object : Callback<Hospital1Items> {
            override fun onResponse(call: Call<Hospital1Items>, response: Response<Hospital1Items>) {
                if (response.isSuccessful) {
                    _hospitalInfo.postValue(response.body())
                } else {
                    // 에러 처리
                }
            }

            override fun onFailure(call: Call<Hospital1Items>, t: Throwable) {
                // 네트워크 오류 처리
            }
        })
    }

}