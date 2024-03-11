package com.kkt1019.hospitalinmyhand.viewmodel

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kkt1019.hospitalinmyhand.R
import com.kkt1019.hospitalinmyhand.data.EmergencyItem
import com.kkt1019.hospitalinmyhand.data.HospitalItem
import com.kkt1019.hospitalinmyhand.data.PharmacyItem
import com.kkt1019.hospitalinmyhand.repository.RoomRepository
import com.kkt1019.hospitalinmyhand.roomdatabase.calender.CalendarDatabase
import com.kkt1019.hospitalinmyhand.roomdatabase.calender.CalendarEntity
import com.kkt1019.hospitalinmyhand.roomdatabase.emergency.EmergencyDataBase
import com.kkt1019.hospitalinmyhand.roomdatabase.emergency.EmergencyEntity
import com.kkt1019.hospitalinmyhand.roomdatabase.hospital.HospitalDataBase
import com.kkt1019.hospitalinmyhand.roomdatabase.hospital.HospitalEntity
import com.kkt1019.hospitalinmyhand.roomdatabase.pharmacy.PharmacyDataBase
import com.kkt1019.hospitalinmyhand.roomdatabase.pharmacy.PharmacyEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class RoomViewModel @Inject constructor(
    private val repository: RoomRepository,
    application: Application
) : AndroidViewModel(application) {

    private val _uiMessage = MutableLiveData<String?>()
    val uiMessage: LiveData<String?> get() = _uiMessage

    fun insertHospitalItem(item: HospitalItem) {
        viewModelScope.launch(Dispatchers.IO){
            val hospitalEntity = HospitalEntity(
                dutyAddr = item.dutyAddr, dutyName = item.dutyName, dutyTell = item.dutyTell, dutyTime1s = item.dutyTime1s,
                dutyTime2s = item.dutyTime2s, dutyTime2c = item.dutyTime2c, dutyTime3s = item.dutyTime3s, dutyTime3c = item.dutyTime3c,
                dutyTime4s = item.dutyTime4s, dutyTime4c = item.dutyTime4c, dutyTime5s = item.dutyTime5s, dutyTime5c = item.dutyTime5c,
                dutyTime6s = item.dutyTime6s, dutyTime6c = item.dutyTime6c, wgs84Lat = item.wgs84Lat, wgs84Lon = item.wgs84Lon,
                hpid = item.hpid, dgidIdName = item.dgidIdName, location = item.location) // 마커 아이콘 수정

            _uiMessage.postValue(repository.insertHospitalItem(hospitalEntity))
        }
    }

    fun insertEmergencyItem(item: EmergencyItem) {
        viewModelScope.launch(Dispatchers.IO){
            val emergencyEntity = EmergencyEntity(
                dutyAddr = item.dutyAddr, dutyName = item.dutyName, dutyTel1 = item.dutyTel1, dutyTel3 = item.dutyTel3, wgs84Lat = item.wgs84Lat,
                wgs84Lon = item.wgs84Lon, hpid = item.hpid, location = item.location) // 마커 아이콘 수정) // 마커 아이콘 수정

            _uiMessage.postValue(repository.insertEmergencyItem(emergencyEntity))
        }
    }

    fun insertPharmacyItem(item: PharmacyItem) {
        viewModelScope.launch(Dispatchers.IO){
            val pharmacyEntity = PharmacyEntity(
                yadmNm = item.yadmNm, addr = item.addr, telno = item.telno, xPos = item.xPos, yPos = item.yPos,
                ykiho = item.ykiho, location = item.location)

            _uiMessage.postValue(repository.insertPharmacyItem(pharmacyEntity))
        }
    }

    fun deletePharmacyItem(ykiho: String) {
        viewModelScope.launch(Dispatchers.IO) {
            // PharmacyDataBase 또는 repository를 통해 해당 아이템 삭제
            val result = repository.deletePharmacyItem(ykiho)
            withContext(Dispatchers.Main) {
                if (result > 0) {
                    _uiMessage.postValue("삭제완료.")
                } else {
                    _uiMessage.postValue("에러 잠시 후에 다시 시도 해 주세요.")
                }
            }
        }
    }

    fun deleteHospitalItem(hpid: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val result = repository.deleteHospitalItem(hpid)
            withContext(Dispatchers.Main) {
                if (result > 0) {
                    _uiMessage.postValue("삭제완료.")
                } else {
                    _uiMessage.postValue("에러 잠시 후에 다시 시도 해 주세요.")
                }
            }
        }
    }

    fun deleteEmergencyItem(hpid: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val result = repository.deleteEmergencyItem(hpid)
            withContext(Dispatchers.Main) {
                if (result > 0) {
                    _uiMessage.postValue("삭제완료.")
                } else {
                    _uiMessage.postValue("에러 잠시 후에 다시 시도 해 주세요.")
                }
            }
        }
    }

}