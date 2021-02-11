package com.piashcse.experiment.mvvm_hilt.datasource

import com.piashcse.experiment.mvvm_hilt.model.RepositoriesModel
import com.piashcse.experiment.mvvm_hilt.repository.DataRepository
import retrofit2.Response
import javax.inject.Inject



class DataSource @Inject constructor(private val repository: DataRepository) {
    suspend fun getRepositoryList(since : String): Response<RepositoriesModel> {
        return repository.getRepositoryList(since)
    }
}