package com.kkt1019.hospitalinmyhand.repository

import com.kkt1019.hospitalinmyhand.roomdatabase.calender.CalendarDao
import com.kkt1019.hospitalinmyhand.roomdatabase.calender.CalendarEntity
import javax.inject.Inject

class CalendarRepository @Inject constructor(
    private val calendarDao: CalendarDao
) {

    suspend fun addEvent(event: CalendarEntity) {
        calendarDao.insertEvent(event)
    }

    suspend fun getAllEvents(): List<CalendarEntity> {
        return calendarDao.getAllEvents()
    }

    suspend fun getEventsByDate(date: Long): List<CalendarEntity> {
        return calendarDao.getEventsByDate(date)
    }
}