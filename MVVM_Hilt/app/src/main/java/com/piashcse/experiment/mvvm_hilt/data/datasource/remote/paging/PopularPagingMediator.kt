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
import retrofit2.HttpException
import java.io.IOException

const val STARTING_PAGE_INDEX = 1

@OptIn(ExperimentalPagingApi::class)
class PopularPagingMediator(
    private val api: ApiService,
    private val db: MovieDatabase
) : RemoteMediator<Int, MovieItem>() {

    override suspend fun initialize(): InitializeAction {
        return InitializeAction.LAUNCH_INITIAL_REFRESH
    }

    override suspend fun load(
        loadType: LoadType, state: PagingState<Int, MovieItem>
    ): MediatorResult {
        val pageKeyData = getKeyPageData(loadType, state)
        val page = when (pageKeyData) {
            is MediatorResult.Success -> {
                return pageKeyData
            }
            else -> {
                pageKeyData as Int
            }
        }

        try {
            val response = api.popularMovieList(page = page)
            val isEndOfList = response.results.isEmpty()
            db.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    db.getMovieDao().deleteAll()
                    db.getKeysDao().deleteAll()
                }
                val prevKey = if (page == STARTING_PAGE_INDEX) null else page - 1
                val nextKey = if (isEndOfList) null else page + 1
                val keys = response.results.map {
                    RemoteKey(it.id, prevKey = prevKey, nextKey = nextKey)
                }
                db.getKeysDao().insertAll(keys)
                db.getMovieDao().insertAll(response.results)
            }
            return MediatorResult.Success(endOfPaginationReached = isEndOfList)
        } catch (exception: IOException) {
            return MediatorResult.Error(exception)
        } catch (exception: HttpException) {
            return MediatorResult.Error(exception)
        }
    }

    private suspend fun getKeyPageData(loadType: LoadType, state: PagingState<Int, MovieItem>): Any {
        return when (loadType) {
            LoadType.REFRESH -> {
                val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                remoteKeys?.nextKey?.minus(1) ?: STARTING_PAGE_INDEX
            }
            LoadType.APPEND -> {
                val remoteKeys = getLastRemoteKey(state)
                val nextKey = remoteKeys?.nextKey
                return nextKey ?: MediatorResult.Success(endOfPaginationReached = false)
            }
            LoadType.PREPEND -> {
                val remoteKeys = getFirstRemoteKey(state)
                val prevKey = remoteKeys?.prevKey ?: return MediatorResult.Success(
                    endOfPaginationReached = false
                )
                prevKey
            }
        }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(state: PagingState<Int, MovieItem>): RemoteKey? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { repoId ->
                db.getKeysDao().remoteKeysMovieId(repoId)
            }
        }
    }

    private suspend fun getLastRemoteKey(state: PagingState<Int, MovieItem>): RemoteKey? {
        return state.pages
            .lastOrNull { it.data.isNotEmpty() }
            ?.data?.lastOrNull()
            ?.let { movie -> db.getKeysDao().remoteKeysMovieId(movie.id) }
    }

    private suspend fun getFirstRemoteKey(state: PagingState<Int,  MovieItem>): RemoteKey? {
        return state.pages
            .firstOrNull { it.data.isNotEmpty() }
            ?.data?.firstOrNull()
            ?.let { movie -> db.getKeysDao().remoteKeysMovieId(movie.id) }
    }


}