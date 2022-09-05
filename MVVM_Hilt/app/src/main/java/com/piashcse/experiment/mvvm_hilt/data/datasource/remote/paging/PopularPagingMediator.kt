package com.piashcse.experiment.mvvm_hilt.data.datasource.remote.paging

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.piashcse.experiment.mvvm_hilt.data.datasource.local.room.MovieDatabase
import com.piashcse.experiment.mvvm_hilt.data.datasource.local.room.movie.RemoteKey
import com.piashcse.experiment.mvvm_hilt.data.datasource.remote.ApiService
import com.piashcse.experiment.mvvm_hilt.data.model.movie.MovieItem
import java.util.concurrent.TimeUnit

@OptIn(ExperimentalPagingApi::class)
class PopularPagingMediator(
    private val api: ApiService,
    private val db: MovieDatabase
) : RemoteMediator<Int, MovieItem>() {
    private val remoteKeyDao = db.remoteKeyDao()
    private val movieDao = db.getMovieDao()

    override suspend fun initialize(): InitializeAction {
        val remoteKey = db.withTransaction {
            remoteKeyDao.getKeyByMovie("popular_movie")
        } ?: return InitializeAction.LAUNCH_INITIAL_REFRESH

        val cacheTimeout = TimeUnit.HOURS.convert(1, TimeUnit.MILLISECONDS)

        return if ((System.currentTimeMillis() - remoteKey.last_updated) >= cacheTimeout) {
            InitializeAction.SKIP_INITIAL_REFRESH
        } else {
            InitializeAction.LAUNCH_INITIAL_REFRESH
        }
    }

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, MovieItem>
    ): MediatorResult {
        return try {
            val page = when (loadType) {
                LoadType.REFRESH -> {
                    1
                }
                LoadType.PREPEND -> {
                    return MediatorResult.Success(true)
                }
                LoadType.APPEND -> {
                    val remoteKey = db.withTransaction {
                        remoteKeyDao.getKeyByMovie("popular_movie")
                    } ?: return MediatorResult.Success(true)

                    if (remoteKey.next_page == null) {
                        return MediatorResult.Success(true)
                    }
                    remoteKey.next_page
                }
            }

            val result = api.popularMovieList(page)

            db.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    movieDao.clearMovies()
                }
                val movieEntities = result.results

                remoteKeyDao.insertKey(
                    RemoteKey(
                        id = "popular_movie",
                        next_page = if (result.results.isNotEmpty()) page + 1 else null,
                        last_updated = System.currentTimeMillis()
                    )
                )
                movieDao.insertAll(movieEntities)
            }

            MediatorResult.Success(
                endOfPaginationReached = result.results.isEmpty()
            )
        } catch (e: Exception) {
            return MediatorResult.Error(e)
        }
    }
}