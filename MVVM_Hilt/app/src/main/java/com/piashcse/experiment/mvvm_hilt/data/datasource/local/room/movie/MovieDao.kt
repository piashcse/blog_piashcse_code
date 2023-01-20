package com.piashcse.experiment.mvvm_hilt.data.datasource.local.room.movie

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.piashcse.experiment.mvvm_hilt.data.model.movie.MovieItem
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(cats: List<MovieItem>)

    @Query("delete from movie")
    suspend fun clearMovies()

    @Query("select * from movie order by id asc")
    fun getAll(): PagingSource<Int, MovieItem>

    @Query("select * from movie order by id asc limit :limit")
    fun getFlowMovies(limit: Int): Flow<List<MovieItem>>
}