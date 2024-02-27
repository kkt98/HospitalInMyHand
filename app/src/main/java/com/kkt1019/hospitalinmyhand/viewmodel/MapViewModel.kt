package com.kkt1019.hospitalinmyhand.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kkt1019.hospitalinmyhand.data.KakaoSearchPlaceItemVO
import com.kkt1019.hospitalinmyhand.network.RetrofitHelper
import com.kkt1019.hospitalinmyhand.network.RetrofitService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MapViewModel : ViewModel() {

    private val _searchPlaceResponse = MutableLiveData<KakaoSearchPlaceItemVO?>()
    val searchPlaceResponse: LiveData<KakaoSearchPlaceItemVO?> = _searchPlaceResponse

    fun searchPlaces(searchQuery: String, lat: String, lng: String) {
        viewModelScope.launch {
            val retrofit = RetrofitHelper.getRetrofitKakaoLocation()
            val response = retrofit.create(RetrofitService::class.java).searchPlaces(searchQuery, lat, lng).execute()
            if (response.isSuccessful) {
                _searchPlaceResponse.postValue(response.body())
            } else {
                // 에러 처리
                _searchPlaceResponse.postValue(null)
            }
        }
    }
}