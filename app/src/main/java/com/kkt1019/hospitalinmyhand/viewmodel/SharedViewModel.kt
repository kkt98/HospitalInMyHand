package com.kkt1019.hospitalinmyhand.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kkt1019.hospitalinmyhand.data.HomePage1Item

class SharedViewModel: ViewModel() {

    private val _hospitalItem = MutableLiveData<HomePage1Item>()
    val hospitalItem: LiveData<HomePage1Item> = _hospitalItem

    fun selectHospitalItem(item: HomePage1Item) {
        _hospitalItem.value = item
    }

}