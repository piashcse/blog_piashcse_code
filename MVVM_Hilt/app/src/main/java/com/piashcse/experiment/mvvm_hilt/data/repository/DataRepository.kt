package com.piashcse.experiment.mvvm_hilt.data.repository

import android.nfc.tech.MifareUltralight.PAGE_SIZE
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.piashcse.experiment.mvvm_hilt.data.datasource.local.room.MovieDatabase
import com.piashcse.experiment.mvvm_hilt.data.model.RepositoriesModel
import com.piashcse.experiment.mvvm_hilt.data.datasource.remote.ApiService
import com.piashcse.experiment.mvvm_hilt.data.datasource.remote.paging.PopularPagingDataSource
import com.piashcse.experiment.mvvm_hilt.data.datasource.remote.paging.PopularPagingMediator
import com.piashcse.experiment.mvvm_hilt.data.model.movie.MovieItem
import com.piashcse.experiment.mvvm_hilt.utils.network.DataState
import com.piashcse.experiment.mvvm_hilt.utils.network.Resource
import com.piashcse.experiment.mvvm_hilt.utils.jsonData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Response
import java.lang.Exception
import javax.inject.Inject

class DataRepository @Inject constructor(private val apiService: ApiService, private val movieDatabase: MovieDatabase) {
    suspend fun getRepositoryList(since: String): Response<RepositoriesModel> {
        return apiService.getGitHubRepositories(since)
    }

    suspend fun githubRepositories(since: String): Flow<DataState<RepositoriesModel>> = flow {
        emit(DataState.Loading)
        try {
            val result = apiService.githubRepositories(since)
            emit(DataState.Success(result))
        } catch (e: Exception) {
            emit(DataState.Error(e))
        }

    }

    suspend fun getRepositoryListFlow(since: String) = flow {
        emit(Resource.loading())
        try {
            val response = apiService.getGitHubRepositories(since)
            if (response.isSuccessful) {
                emit(Resource.success(response.body()))
            } else {
                emit(Resource.error(response.errorBody()?.jsonData()))
            }

        } catch (e: Throwable) {
            emit(Resource.error(e))
        }
    }

    suspend fun popularMovieList(page:Int) = flow {
        emit(DataState.Loading)
        try {
            val result = apiService.popularMovieList(page)
            emit(DataState.Success(result.results))
        } catch (e: Exception) {
            emit(DataState.Error(e))
        }

    }

    suspend fun topRatedMovieList(page: Int) = flow {
        emit(DataState.Loading)
        try {
            val result = apiService.topRatedMovieList(page)
            emit(DataState.Success(result.results))
        } catch (e: Exception) {
            emit(DataState.Error(e))
        }

    }

   fun popularPagingDataSource() = Pager(
       // Configure how data is loaded by passing additional properties to
       // PagingConfig, such as prefetchDistance.
       pagingSourceFactory = { PopularPagingDataSource(apiService) },
       config = PagingConfig(pageSize = 2)
   ).flow

    @ExperimentalPagingApi
    fun getMovieFromMediator(): Flow<PagingData<MovieItem>> {
        val pagingSourceFactory = { movieDatabase.getMovieDao().getAll() }

        return Pager(
            config = PagingConfig(
                pageSize = PAGE_SIZE,
                maxSize = PAGE_SIZE + (PAGE_SIZE * 2),
                enablePlaceholders = false,
            ),
            remoteMediator = PopularPagingMediator(
                apiService,
                movieDatabase
            ),
            pagingSourceFactory = pagingSourceFactory
        ).flow
    }
}