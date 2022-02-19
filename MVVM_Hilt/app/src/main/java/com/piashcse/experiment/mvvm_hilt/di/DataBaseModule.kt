package com.piashcse.experiment.mvvm_hilt.di

import android.content.Context
import androidx.room.Room
import com.piashcse.experiment.mvvm_hilt.data.datasource.local.DataStoreManager
import com.piashcse.experiment.mvvm_hilt.data.datasource.local.room.AppDatabase
import com.piashcse.experiment.mvvm_hilt.data.datasource.local.room.UserDao
import com.piashcse.experiment.mvvm_hilt.data.repository.RoomDBRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
object DataBaseModule {

    @Provides
    fun provideDataStoreManager(@ApplicationContext context: Context): DataStoreManager {
        return DataStoreManager(context)
    }

    @Provides
    fun provideDB(@ApplicationContext applicationContext: Context) = Room.databaseBuilder(
        applicationContext,
        AppDatabase::class.java,
        "user_database"
    ).build()

    @Provides
    fun provideYourDao(db: AppDatabase) = db.userDao()


    @Provides
    fun provideRoomRepository(userDao: UserDao) = RoomDBRepository(userDao)


}