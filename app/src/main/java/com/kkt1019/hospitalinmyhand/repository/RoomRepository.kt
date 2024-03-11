package com.kkt1019.hospitalinmyhand.repository

import com.kkt1019.hospitalinmyhand.roomdatabase.emergency.EmergencyDao
import com.kkt1019.hospitalinmyhand.roomdatabase.emergency.EmergencyEntity
import com.kkt1019.hospitalinmyhand.roomdatabase.hospital.HospitalDao
import com.kkt1019.hospitalinmyhand.roomdatabase.hospital.HospitalEntity
import com.kkt1019.hospitalinmyhand.roomdatabase.pharmacy.PharmacyDao
import com.kkt1019.hospitalinmyhand.roomdatabase.pharmacy.PharmacyEntity
import javax.inject.Inject

class RoomRepository @Inject constructor(
    private val hospitalDao: HospitalDao,
    private val emergencyDao: EmergencyDao,
    private val pharmacyDao: PharmacyDao) {

    suspend fun insertHospitalItem(item: HospitalEntity): String {
        return if (!hospitalDao.exists(item.hpid!!)) {
            hospitalDao.insertAll(item)
            "저장 완료."
        } else {
            "이미 저장된 항목입니다."
        }
    }

        suspend fun insertEmergencyItem(item: EmergencyEntity) :String {
            return if (!emergencyDao.exists(item.hpid)) {
                emergencyDao.insertAll(item)
                "저장 완료."
            } else {
                "이미 저장된 항목입니다."
            }
        }

        suspend fun insertPharmacyItem(item: PharmacyEntity) : String {
            return if (!pharmacyDao.exists(item.ykiho)) {
                pharmacyDao.insertAll(item)
                "저장 완료."
            } else {
                "이미 저장된 항목입니다."
            }
        }

    suspend fun deletePharmacyItem(ykiho: String): Int {
        return pharmacyDao.deleteByYkiho(ykiho)
    }

    suspend fun deleteHospitalItem(hpid: String): Int {
        return hospitalDao.deleteByHospital(hpid)
    }

    suspend fun deleteEmergencyItem(hpid: String): Int {
        return emergencyDao.deleteByEmergency(hpid)
    }

}