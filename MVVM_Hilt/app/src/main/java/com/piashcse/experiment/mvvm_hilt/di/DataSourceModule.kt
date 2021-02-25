package com.piashcse.experiment.mvvm_hilt.di

import com.piashcse.experiment.mvvm_hilt.datasource.DataSource
import com.piashcse.experiment.mvvm_hilt.repository.DataRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DataSourceModule {

    @Provides
    fun provideDataSource(dataRepository: DataRepository): DataSource {
        return DataSource(dataRepository)
    }
}