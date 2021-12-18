package com.piashcse.experiment.mvvm_hilt.datasource.remote

import com.piashcse.experiment.mvvm_hilt.constants.AppConstants
import com.piashcse.experiment.mvvm_hilt.model.RepoSearchResponse
import com.piashcse.experiment.mvvm_hilt.model.RepositoriesModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET(AppConstants.URL_REPOSITORIES)
    suspend fun getGitHubRepositories(
        @Query("since") since: String
    ): Response<RepositoriesModel>

    @GET(AppConstants.URL_REPOSITORIES)
    suspend fun githubRepositories(
        @Query("since") since: String
    ): RepositoriesModel

    @GET(AppConstants.URL_SEARCH_REPOSITORIES)
    suspend fun searchRepos(@Query("q") query: String): Response<RepoSearchResponse>
}