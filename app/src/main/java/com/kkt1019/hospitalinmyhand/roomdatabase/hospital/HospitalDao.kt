package com.kkt1019.hospitalinmyhand.roomdatabase.hospital

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface HospitalDao {

    @Query("SELECT * FROM hospitalDataBase")
    suspend fun getAll(): List<HospitalEntity>

    @Delete
    suspend fun delete(hospitalEntity: HospitalEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(vararg hospitalEntity: HospitalEntity)

    @Query("SELECT EXISTS(SELECT 1 FROM hospitalDataBase WHERE hpid = :hpid LIMIT 1)")
    suspend fun exists(hpid: String): Boolean

}