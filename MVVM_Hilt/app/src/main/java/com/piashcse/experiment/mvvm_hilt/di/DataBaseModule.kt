package com.piashcse.experiment.mvvm_hilt.di

import android.content.Context
import androidx.room.Room
import com.piashcse.experiment.mvvm_hilt.data.datasource.local.DataStoreManager
import com.piashcse.experiment.mvvm_hilt.data.datasource.local.room.AppDatabase
import com.piashcse.experiment.mvvm_hilt.data.datasource.local.room.MovieDatabase
import com.piashcse.experiment.mvvm_hilt.data.datasource.local.room.UserDao
import com.piashcse.experiment.mvvm_hilt.data.datasource.local.room.movie.MovieDao
import com.piashcse.experiment.mvvm_hilt.data.repository.RoomDBRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object DataBaseModule {

    /**
     * Provides DataStoreManger
     */
    @Singleton
    @Provides
    fun provideDataStoreManager(@ApplicationContext context: Context): DataStoreManager {
        return DataStoreManager(context)
    }

    /**
     * Provides AppDatabase
     */
    @Singleton
    @Provides
    fun provideDB(@ApplicationContext applicationContext: Context) = Room.databaseBuilder(
        applicationContext,
        AppDatabase::class.java,
        "user_database"
    ).build()

    /**
     * Provides MovieDatabase
     */
    @Singleton
    @Provides
    fun provideMovieDB(@ApplicationContext applicationContext: Context) = Room.databaseBuilder(
        applicationContext,
        MovieDatabase::class.java,
        "movie_database"
    ).build()

    /**
     * Provides userDao an object to access user table from Database
     */
    @Singleton
    @Provides
    fun provideYourDao(db: AppDatabase) = db.userDao()

    /**
     * Provides RoomDBRepository object
     */
    @Singleton
    @Provides
    fun provideRoomRepository(userDao: UserDao) = RoomDBRepository(userDao)

    /**
     * Provides MovieDao an object to access movie table from Database
     */
    @Singleton
    @Provides
    fun provideMovieDao(db: MovieDatabase) = db.getMovieDao()

}