package com.piashcse.experiment.mvvm_hilt.datasource.remote

import com.piashcse.experiment.mvvm_hilt.datasource.model.RepoSearchResponse
import com.piashcse.experiment.mvvm_hilt.datasource.model.RepositoriesModel
import retrofit2.Response
import javax.inject.Inject


class DataSource @Inject constructor(private val apiService: ApiService) {

    suspend fun getRepositoryList(since: String): Response<RepositoriesModel> {
        return apiService.getGitHubRepositories(since)
    }

    suspend fun githubRepositories(since: String): RepositoriesModel {
        return apiService.githubRepositories(since)
    }

    suspend fun getRepositoryListFlow(since: String): Response<RepoSearchResponse> {
        return apiService.searchRepos(since)
    }
}