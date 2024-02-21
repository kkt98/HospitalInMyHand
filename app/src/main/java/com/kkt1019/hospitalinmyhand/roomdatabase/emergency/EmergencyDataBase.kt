package com.kkt1019.hospitalinmyhand.roomdatabase.emergency

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [EmergencyEntity::class], version = 1, exportSchema = false)
abstract class EmergencyDataBase: RoomDatabase() {

    abstract fun emergencyDao() : EmergencyDao

    companion object {
        @Volatile
        private var INSTANCE: EmergencyDataBase? = null

        fun getDatabase(context: Context): EmergencyDataBase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    EmergencyDataBase::class.java,
                    "emergency_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}