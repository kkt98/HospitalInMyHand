package com.kkt1019.hospitalinmyhand.roomdata

import android.content.Context
import androidx.room.Database
import androidx.room.DatabaseConfiguration
import androidx.room.InvalidationTracker
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteOpenHelper

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