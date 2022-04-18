package com.piashcse.experiment.mvvm_hilt.di

import com.piashcse.experiment.mvvm_hilt.data.datasource.local.room.MovieDatabase
import com.piashcse.experiment.mvvm_hilt.data.datasource.remote.ApiService
import com.piashcse.experiment.mvvm_hilt.data.datasource.remote.paging.PopularPagingDataSource
import com.piashcse.experiment.mvvm_hilt.data.repository.DataRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun provideDataRepository(apiService: ApiService, movieDatabase: MovieDatabase): DataRepository {
        return DataRepository(apiService, movieDatabase)
    }
}