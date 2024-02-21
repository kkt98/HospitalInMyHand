package com.kkt1019.hospitalinmyhand.roomdatabase.pharmacy

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.kkt1019.hospitalinmyhand.roomdatabase.hospital.HospitalDao
import com.kkt1019.hospitalinmyhand.roomdatabase.hospital.HospitalDataBase

@Database(entities = [PharmacyEntity::class], version = 1, exportSchema = false)
abstract class PharmacyDataBase: RoomDatabase() {

    abstract fun pharmacyDao(): PharmacyDao
    companion object {
        @Volatile
        private var INSTANCE: PharmacyDataBase? = null

        fun getDatabase(context: Context): PharmacyDataBase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    PharmacyDataBase::class.java,
                    "pharmacy_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }

}