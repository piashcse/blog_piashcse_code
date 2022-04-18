package com.piashcse.experiment.mvvm_hilt.data.datasource.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.piashcse.experiment.mvvm_hilt.data.datasource.local.room.movie.MovieDao
import com.piashcse.experiment.mvvm_hilt.data.datasource.local.room.movie.RemoteKey
import com.piashcse.experiment.mvvm_hilt.data.datasource.local.room.movie.RemoteKeyDao
import com.piashcse.experiment.mvvm_hilt.data.model.movie.IntTypeConverter
import com.piashcse.experiment.mvvm_hilt.data.model.movie.MovieItem

@Database(version = 1, entities = [MovieItem::class, RemoteKey::class], exportSchema = false)
@TypeConverters(IntTypeConverter::class)
abstract class MovieDatabase : RoomDatabase() {
    abstract fun getMovieDao(): MovieDao
    abstract fun getKeysDao(): RemoteKeyDao
}