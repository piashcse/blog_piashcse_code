package com.piashcse.experiment.mvvm_hilt.repository

import com.piashcse.experiment.mvvm_hilt.datasource.remote.DataSource
import com.piashcse.experiment.mvvm_hilt.model.RepositoriesModel
import com.piashcse.experiment.mvvm_hilt.network.DataState
import com.piashcse.experiment.mvvm_hilt.network.Resource
import com.piashcse.experiment.mvvm_hilt.utils.jsonData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Response
import java.lang.Exception
import javax.inject.Inject

class DataRepository @Inject constructor(private val dataSource: DataSource) {
    suspend fun getRepositoryList(since: String): Response<RepositoriesModel> {
        return dataSource.getRepositoryList(since)

    }

    suspend fun githubRepositories(since: String): Flow<DataState<RepositoriesModel>> = flow {
        emit(DataState.Loading)
        try {
            val result = dataSource.githubRepositories(since)
            emit(DataState.Success(result))
        } catch (e: Exception) {
            emit(DataState.Error(e))
        }

    }

    suspend fun getRepositoryListFlow(since: String) = flow {
        emit(Resource.loading())
        try {
            val response = dataSource.getRepositoryListFlow(since)
            if (response.isSuccessful) {
                emit(Resource.success(response.body()))
            } else {
                emit(Resource.error(response.errorBody()?.jsonData()))
            }

        } catch (e: Throwable) {
            emit(Resource.error(e))
        }
    }
}