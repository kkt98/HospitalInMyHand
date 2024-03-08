package com.kkt1019.hospitalinmyhand

import android.app.Application
import com.kkt1019.hospitalinmyhand.repository.EmergencyRepository
import com.kkt1019.hospitalinmyhand.repository.HospitalRepository
import com.kkt1019.hospitalinmyhand.repository.MedicalRepository
import com.kkt1019.hospitalinmyhand.repository.PharmacyRepository
import com.kkt1019.hospitalinmyhand.repository.RoomRepository
import com.kkt1019.hospitalinmyhand.roomdatabase.calender.CalendarDao
import com.kkt1019.hospitalinmyhand.roomdatabase.calender.CalendarDatabase
import com.kkt1019.hospitalinmyhand.roomdatabase.emergency.EmergencyDao
import com.kkt1019.hospitalinmyhand.roomdatabase.emergency.EmergencyDataBase
import com.kkt1019.hospitalinmyhand.roomdatabase.hospital.HospitalDao
import com.kkt1019.hospitalinmyhand.roomdatabase.hospital.HospitalDataBase
import com.kkt1019.hospitalinmyhand.roomdatabase.pharmacy.PharmacyDao
import com.kkt1019.hospitalinmyhand.roomdatabase.pharmacy.PharmacyDataBase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideCalendarDao(application: Application): CalendarDao =
        CalendarDatabase.getDatabase(application).calendarDao()

    @Provides
    @Singleton
    fun provideHospitalDao(application: Application): HospitalDao =
        HospitalDataBase.getDatabase(application).hospitalDao()

    @Provides
    @Singleton
    fun provideEmergencyDao(application: Application): EmergencyDao =
        EmergencyDataBase.getDatabase(application).emergencyDao()

    @Provides
    @Singleton
    fun providePharmacyDao(application: Application): PharmacyDao =
        PharmacyDataBase.getDatabase(application).pharmacyDao()

    @Provides
    @Singleton
    fun provideRoomRepository(
        hospitalDao: HospitalDao,
        emergencyDao: EmergencyDao,
        pharmacyDao: PharmacyDao
    ): RoomRepository {
        return RoomRepository(hospitalDao, emergencyDao, pharmacyDao)
    }

    @Provides
    @Singleton
    fun provideEmergencyRepository() : EmergencyRepository {
        return EmergencyRepository()
    }

    @Provides
    @Singleton
    fun provideHospitalRepository() : HospitalRepository {
        return HospitalRepository()
    }

    @Provides
    @Singleton
    fun provideMedicalRepository() : MedicalRepository {
        return MedicalRepository()
    }

    @Provides
    @Singleton
    fun providePharmacyRepository() : PharmacyRepository {
        return PharmacyRepository()
    }
}
