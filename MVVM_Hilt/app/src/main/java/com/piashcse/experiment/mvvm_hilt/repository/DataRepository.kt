package com.piashcse.experiment.mvvm_hilt.repository

import com.piashcse.experiment.mvvm_hilt.datasource.remote.DataSource
import com.piashcse.experiment.mvvm_hilt.model.RepositoriesModel
import com.piashcse.experiment.mvvm_hilt.network.Resource
import com.piashcse.experiment.mvvm_hilt.utils.jsonData
import kotlinx.coroutines.flow.flow
import retrofit2.Response
import javax.inject.Inject

class DataRepository @Inject constructor(private val dataSource: DataSource) {
    suspend fun getRepositoryList(since: String): Response<RepositoriesModel> {
        return dataSource.getRepositoryList(since)

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