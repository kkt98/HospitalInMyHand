package com.kkt1019.hospitalinmyhand.roomdatabase.calender

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface CalendarDao {
    @Insert
    suspend fun insertEvent(calendarEntity: CalendarEntity)

    @Query("SELECT * FROM calendarEvents WHERE date = :date")
    suspend fun getEventsByDate(date: Long): List<CalendarEntity>

    @Query("SELECT * FROM calendarEvents")
    suspend fun getAllEvents(): List<CalendarEntity>
}