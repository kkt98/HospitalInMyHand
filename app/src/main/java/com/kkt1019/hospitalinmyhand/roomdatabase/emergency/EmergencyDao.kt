package com.kkt1019.hospitalinmyhand.roomdatabase.emergency

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.kkt1019.hospitalinmyhand.roomdatabase.hospital.HospitalEntity

@Dao
interface EmergencyDao {

    @Query("SELECT * FROM emergencyDataBase")
    suspend fun getAll(): List<EmergencyEntity>

    @Delete
    suspend fun delete(emergencyEntity: EmergencyEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(vararg emergencyEntity: EmergencyEntity)

    @Query("SELECT EXISTS(SELECT 1 FROM emergencyDataBase WHERE hpid = :hpid LIMIT 1)")
    suspend fun exists(hpid: String): Boolean


}