package com.kkt1019.hospitalinmyhand.roomdatabase.hospital

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [HospitalEntity::class], version = 1, exportSchema = false)
abstract class HospitalDataBase : RoomDatabase()  {
    abstract fun hospitalDao(): HospitalDao
    companion object {
        @Volatile
        private var INSTANCE: HospitalDataBase? = null

        fun getDatabase(context: Context): HospitalDataBase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    HospitalDataBase::class.java,
                    "hospital_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }

}
