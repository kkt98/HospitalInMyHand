package com.kkt1019.hospitalinmyhand.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kkt1019.hospitalinmyhand.MedicalItemVO
import com.kkt1019.hospitalinmyhand.MedicalItems
import com.kkt1019.hospitalinmyhand.network.RetrofitHelper
import com.kkt1019.hospitalinmyhand.network.RetrofitService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MedicalViewModel: ViewModel() {

    private val _medicalItems = MutableLiveData<List<MedicalItems>>()
    val medicalItems: LiveData<List<MedicalItems>> = _medicalItems

    fun fetchMedicalData(etname: String) {
        val retrofit = RetrofitHelper.getRetrofitInstance()
        val retrofitService = retrofit.create(RetrofitService::class.java)
        val call = retrofitService.MedicalData("H7PvoIiO2D6+qVfe6kF2WAoJgdpbVUtJT52Wx7dL6+DLP4IEk5i5xqP+GZMDktix9xaYS03X6YP4JtLGSnuunw==",
            etname, "json")

        call.enqueue(object : Callback<MedicalItemVO> {
            override fun onResponse(call: Call<MedicalItemVO>, response: Response<MedicalItemVO>) {
                val medicalResponse: MedicalItemVO? = response.body()
                medicalResponse?.body?.items?.let {
                    _medicalItems.postValue(it)
                }
            }

            override fun onFailure(call: Call<MedicalItemVO>, t: Throwable) {
                Log.e("MedicalViewModel", "Error fetching medical data", t)
            }
        })
    }

}