package com.kkt1019.hospitalinmyhand.roomdatabase.pharmacy

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.kkt1019.hospitalinmyhand.roomdatabase.hospital.HospitalEntity

@Dao
interface PharmacyDao {

    @Query("SELECT * FROM pharmacyDataBase")
    suspend fun getAll(): List<PharmacyEntity>

    @Delete
    suspend fun delete(pharmacyEntity: PharmacyEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(vararg pharmacyEntity: PharmacyEntity)

    @Query("SELECT EXISTS(SELECT 1 FROM pharmacyDataBase WHERE ykiho = :ykiho LIMIT 1)")
    suspend fun exists(ykiho: String): Boolean

}