package com.kkt1019.hospitalinmyhand.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kkt1019.hospitalinmyhand.data.HospitalItem
import com.kkt1019.hospitalinmyhand.data.EmergencyItem
import com.kkt1019.hospitalinmyhand.data.PharmacyItem

class SharedViewModel: ViewModel() {

    private val _hospitalItem = MutableLiveData<HospitalItem>()
    val hospitalItem: LiveData<HospitalItem> = _hospitalItem

    private val _emergencyItem = MutableLiveData<EmergencyItem>()
    val emergencyItem: LiveData<EmergencyItem> = _emergencyItem

    private val _pharmacyItem = MutableLiveData<PharmacyItem>()
    val pharmacyItem: LiveData<PharmacyItem> = _pharmacyItem

    fun selectHospitalItem(item: HospitalItem) {
        _hospitalItem.value = item
    }

    fun selectEmergencyItem(item: EmergencyItem) {
        _emergencyItem.value = item
    }

    fun selectPharmacyItem(item: PharmacyItem) {
        _pharmacyItem.value = item
    }

}