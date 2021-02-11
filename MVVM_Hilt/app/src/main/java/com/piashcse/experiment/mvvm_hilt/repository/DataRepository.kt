package com.piashcse.experiment.mvvm_hilt.repository

import com.piashcse.experiment.mvvm_hilt.model.RepositoriesModel
import com.piashcse.experiment.mvvm_hilt.network.ApiService
import retrofit2.Response
import javax.inject.Inject

class DataRepository @Inject constructor(private val apiService: ApiService) {
    suspend fun getRepositoryList(since :String): Response<RepositoriesModel> {
       return apiService.getGitHubRepositories(since)
    }
}