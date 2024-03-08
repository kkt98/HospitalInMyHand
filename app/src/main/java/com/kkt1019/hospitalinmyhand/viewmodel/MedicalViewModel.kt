package com.kkt1019.hospitalinmyhand.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kkt1019.hospitalinmyhand.data.MedicalItemVO
import com.kkt1019.hospitalinmyhand.data.MedicalItems
import com.kkt1019.hospitalinmyhand.network.RetrofitHelper
import com.kkt1019.hospitalinmyhand.network.RetrofitService
import com.kkt1019.hospitalinmyhand.repository.MedicalRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class MedicalViewModel @Inject constructor(
    private val repository: MedicalRepository
): ViewModel() {

    private val _medicalItems = MutableLiveData<List<MedicalItems>>()
    val medicalItems: LiveData<List<MedicalItems>> = _medicalItems

    fun fetchMedicalData(etname: String) {
        repository.fetchMedicalData(etname,
            onSuccess = { items ->
                _medicalItems.postValue(items)
            },
            onFailure = { error ->
                Log.e("MedicalViewModel", "Error fetching medical data", error)
            }
        )
    }
}