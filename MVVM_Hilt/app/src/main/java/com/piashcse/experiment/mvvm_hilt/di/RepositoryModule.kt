package com.piashcse.experiment.mvvm_hilt.di

import com.piashcse.experiment.mvvm_hilt.datasource.remote.ApiService
import com.piashcse.experiment.mvvm_hilt.datasource.repository.DataRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    fun provideDataRepository(apiService: ApiService): DataRepository {
        return DataRepository(apiService)
    }
}