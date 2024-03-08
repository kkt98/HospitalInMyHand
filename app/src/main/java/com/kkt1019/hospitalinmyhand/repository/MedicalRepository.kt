package com.kkt1019.hospitalinmyhand.repository

import com.kkt1019.hospitalinmyhand.data.MedicalItemVO
import com.kkt1019.hospitalinmyhand.data.MedicalItems
import com.kkt1019.hospitalinmyhand.network.RetrofitHelper
import com.kkt1019.hospitalinmyhand.network.RetrofitService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

class MedicalRepository @Inject constructor() {

    fun fetchMedicalData(etname: String, onSuccess: (List<MedicalItems>) -> Unit, onFailure: (Throwable) -> Unit) {
        val retrofit = RetrofitHelper.getRetrofitInstance()
        val retrofitService = retrofit.create(RetrofitService::class.java)

        val call = retrofitService.MedicalData("H7PvoIiO2D6+qVfe6kF2WAoJgdpbVUtJT52Wx7dL6+DLP4IEk5i5xqP+GZMDktix9xaYS03X6YP4JtLGSnuunw==", etname, "json")
        call.enqueue(object : Callback<MedicalItemVO> {
            override fun onResponse(call: Call<MedicalItemVO>, response: Response<MedicalItemVO>) {
                val medicalResponse: MedicalItemVO? = response.body()
                medicalResponse?.body?.items?.let {
                    onSuccess(it)
                }
            }

            override fun onFailure(call: Call<MedicalItemVO>, t: Throwable) {
                onFailure(t)
            }
        })
    }

}