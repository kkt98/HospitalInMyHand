package com.kkt1019.hospitalinmyhand.roomdatabase.hospital

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "hospitalDataBase")
data class HospitalEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    var dutyAddr: String? = null,
    var dutyName: String? = null,
    var dutyTell: String? = null,
    var dutyTime1s: String? = null,
    var dutyTime1c: String? = null,
    var dutyTime2s: String? = null,
    var dutyTime2c: String? = null,
    var dutyTime3s: String? = null,
    var dutyTime3c: String? = null,
    var dutyTime4s: String? = null,
    var dutyTime4c: String? = null,
    var dutyTime5s: String? = null,
    var dutyTime5c: String? = null,
    var dutyTime6s: String? = null,
    var dutyTime6c: String? = null,
    var wgs84Lat: String? = null,
    var wgs84Lon: String? = null,
    var hpid: String? = null,
    var dgidIdName: String? = null,
    var location: String? = null
)
