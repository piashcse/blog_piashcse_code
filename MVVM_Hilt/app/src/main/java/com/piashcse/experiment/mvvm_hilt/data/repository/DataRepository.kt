package com.piashcse.experiment.mvvm_hilt.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.room.withTransaction
import com.piashcse.experiment.mvvm_hilt.data.datasource.local.room.MovieDatabase
import com.piashcse.experiment.mvvm_hilt.data.datasource.local.room.movie.RemoteKey
import com.piashcse.experiment.mvvm_hilt.data.model.RepositoriesModel
import com.piashcse.experiment.mvvm_hilt.data.datasource.remote.ApiService
import com.piashcse.experiment.mvvm_hilt.data.datasource.remote.paging.PopularPagingDataSource
import com.piashcse.experiment.mvvm_hilt.data.datasource.remote.paging.PopularPagingMediator
import com.piashcse.experiment.mvvm_hilt.data.model.movie.BaseModel
import com.piashcse.experiment.mvvm_hilt.data.model.movie.MovieItem
import com.piashcse.experiment.mvvm_hilt.utils.network.DataState
import com.piashcse.experiment.mvvm_hilt.utils.network.networkBoundResource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Response
import java.lang.Exception
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class DataRepository @Inject constructor(
    private val apiService: ApiService,
    private val movieDatabase: MovieDatabase
) {
    suspend fun getRepositoryList(since: String): Response<RepositoriesModel> {
        return apiService.getGitHubRepositories(since)
    }

    suspend fun githubRepositories(since: String): Flow<DataState<RepositoriesModel>> = flow {
        emit(DataState.Loading)
        try {
            val result = apiService.githubRepositories(since)
            emit(DataState.Success(result))
        } catch (e: Exception) {
            emit(DataState.Error(e.message))
        }

    }

    suspend fun search(searchKey: String): Flow<DataState<BaseModel>> = flow {
        emit(DataState.Loading)
        try {
            val searchResult = apiService.search(searchKey)
            emit(DataState.Success(searchResult))
        } catch (e: Exception) {
            emit(DataState.Error(e.message))
        }
    }

    suspend fun popularMovieList(page: Int) = flow {
        emit(DataState.Loading)
        try {
            val result = apiService.popularMovieList(page)
            emit(DataState.Success(result.results))
        } catch (e: Exception) {
            emit(DataState.Error(e.message))
        }

    }

    suspend fun topRatedMovieList(page: Int) = flow {
        emit(DataState.Loading)
        try {
            val result = apiService.topRatedMovieList(page)
            emit(DataState.Success(result.results))
        } catch (e: Exception) {
            emit(DataState.Error(e.message))
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
                pageSize = 1,
            ),
            remoteMediator = PopularPagingMediator(
                apiService,
                movieDatabase
            ),
            pagingSourceFactory = pagingSourceFactory
        ).flow
    }

    fun popularMovie(page: Int, force: Boolean = true) : Flow<DataState<List<MovieItem>>> {
        return networkBoundResource(
            query = {
                movieDatabase.getMovieDao().getFlowMovies(50)
            },
            fetch = {
                apiService.popularMovieList(page)
            },
            saveFetchResult = { items ->
                val resultEntity = items.results.map {
                    it
                }
                movieDatabase.withTransaction {
                    movieDatabase.getMovieDao().clearMovies()

                    movieDatabase.remoteKeyDao().insertKey(
                        RemoteKey(
                            id = "popular_movie",
                            next_page = page + 1,
                            last_updated = System.currentTimeMillis()
                        )
                    )
                    movieDatabase.getMovieDao().insertAll(resultEntity)
                }
            },
            shouldFetch = {
                if(force) {
                    true
                } else {
                    val remoteKey = movieDatabase.withTransaction {
                        movieDatabase.remoteKeyDao().getKeyByMovie("popular_movie")
                    }

                    if(remoteKey == null) {
                        true
                    }
                    else {
                        val cacheTimeout = TimeUnit.HOURS.convert(1, TimeUnit.MILLISECONDS)

                        (System.currentTimeMillis() - remoteKey.last_updated) < cacheTimeout
                    }
                }
            }
        )
    }
}