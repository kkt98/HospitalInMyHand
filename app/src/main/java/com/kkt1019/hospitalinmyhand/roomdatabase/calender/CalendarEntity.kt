package com.kkt1019.hospitalinmyhand.roomdatabase.calender

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.kkt1019.hospitalinmyhand.R

@Entity(tableName = "calendarEvents")
data class CalendarEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val date: Long, // 날짜
    val content: String, // 사용자가 입력한 일정 내용
    val markerIcon: Int? = R.drawable.ic_light_off // 마커 아이콘의 리소스 이름
)
