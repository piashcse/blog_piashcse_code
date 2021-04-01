package com.piashcse.experiment.mvvm_hilt.datasource.remote

import com.piashcse.experiment.mvvm_hilt.model.RepoSearchResponse
import com.piashcse.experiment.mvvm_hilt.model.RepositoriesModel
import com.piashcse.experiment.mvvm_hilt.network.ApiService
import com.piashcse.experiment.mvvm_hilt.network.Resource
import com.piashcse.experiment.mvvm_hilt.repository.DataRepository
import com.piashcse.experiment.mvvm_hilt.utils.jsonData
import kotlinx.coroutines.flow.flow
import retrofit2.Response
import javax.inject.Inject


class DataSource @Inject constructor(private val apiService: ApiService) {

    suspend fun getRepositoryList(since: String): Response<RepositoriesModel> {
        return apiService.getGitHubRepositories(since)
    }

    suspend fun getRepositoryListFlow(since: String): Response<RepoSearchResponse> {
        return apiService.searchRepos(since)
    }
}