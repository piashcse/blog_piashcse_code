package com.piashcse.experiment.mvvm_hilt.data.datasource.local.room.movie

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.piashcse.experiment.mvvm_hilt.data.model.movie.MovieItem

@Dao
interface MovieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(cats: List<MovieItem>)

    @Query("SELECT * FROM movie")
    fun getAll(): PagingSource<Int, MovieItem>

    @Query("DELETE FROM movie")
    suspend fun deleteAll()
}