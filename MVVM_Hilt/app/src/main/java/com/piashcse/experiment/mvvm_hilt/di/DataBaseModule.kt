package com.piashcse.experiment.mvvm_hilt.di

import android.content.Context
import androidx.room.Room
import com.piashcse.experiment.mvvm_hilt.datasource.local.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object DataBaseModule {
    @Provides
    fun provideDB(@ApplicationContext applicationContext: Context) = Room.databaseBuilder(
        applicationContext,
        AppDatabase::class.java,
        "user_database"
    ).build()

    @Provides
    fun provideYourDao(db: AppDatabase) = db.userDao()
}