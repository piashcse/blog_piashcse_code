package com.piashcse.experiment.mvvm_hilt.di

import com.piashcse.experiment.mvvm_hilt.datasource.remote.DataSource
import com.piashcse.experiment.mvvm_hilt.datasource.remote.ApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DataSourceModule {

    @Provides
    fun provideDataSource(apiService: ApiService): DataSource {
        return DataSource(apiService)
    }
}