package com.kkt1019.hospitalinmyhand.roomdatabase.emergency

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "emergencyDataBase")
data class EmergencyEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    var dutyAddr:String = "",
    var dutyName:String = "",
    var dutyTel1:String = "",
    var dutyTel3:String = "",
    var wgs84Lat:String = "",
    var wgs84Lon:String = "",
    var hpid:String = "",
    var location:String = ""
)
