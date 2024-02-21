package com.kkt1019.hospitalinmyhand.roomdatabase.pharmacy

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "pharmacyDataBase")
data class PharmacyEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    var yadmNm:String = "",
    var addr:String = "",
    var telno:String = "",
    var xPos:String = "",
    var yPos:String = "",
    var ykiho:String = "",
    var location:String = ""
)
