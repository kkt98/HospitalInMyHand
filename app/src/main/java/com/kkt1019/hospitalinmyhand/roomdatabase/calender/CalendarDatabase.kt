package com.kkt1019.hospitalinmyhand.roomdatabase.calender

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [CalendarEntity::class], version = 1, exportSchema = false)
abstract class CalendarDatabase : RoomDatabase() {
    abstract fun calendarDao(): CalendarDao

    companion object {
        @Volatile
        private var INSTANCE: CalendarDatabase? = null

        fun getDatabase(context: Context): CalendarDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    CalendarDatabase::class.java,
                    "calender_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}