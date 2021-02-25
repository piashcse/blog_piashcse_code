package com.piashcse.experiment.mvvm_hilt.repository

import com.piashcse.experiment.mvvm_hilt.model.RepoSearchResponse
import com.piashcse.experiment.mvvm_hilt.model.RepositoriesModel
import com.piashcse.experiment.mvvm_hilt.network.ApiService
import com.piashcse.experiment.mvvm_hilt.network.Resource
import com.piashcse.experiment.mvvm_hilt.utils.jsonData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.Call
import retrofit2.Response
import timber.log.Timber
import java.util.concurrent.Flow
import javax.inject.Inject
import kotlin.math.sin

class DataRepository @Inject constructor(private val apiService: ApiService) {
    suspend fun getRepositoryList(since: String): Response<RepositoriesModel> {
        return apiService.getGitHubRepositories(since)
    }

    suspend fun getRepositoryListFlow(since: String): Response<RepoSearchResponse> {
        return apiService.searchRepos(since)
    }
}