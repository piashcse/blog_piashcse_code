package com.piashcse.experiment.mvvm_hilt.di

import com.piashcse.experiment.mvvm_hilt.datasource.remote.DataSource
import com.piashcse.experiment.mvvm_hilt.repository.DataRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    fun provideDataRepository(dataSource: DataSource): DataRepository {
        return DataRepository(dataSource)
    }
}