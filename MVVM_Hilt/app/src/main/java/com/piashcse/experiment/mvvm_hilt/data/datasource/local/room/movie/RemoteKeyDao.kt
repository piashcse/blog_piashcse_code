package com.piashcse.experiment.mvvm_hilt.data.datasource.local.room.movie

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.piashcse.experiment.mvvm_hilt.data.datasource.local.room.movie.RemoteKey

@Dao
interface RemoteKeyDao {

   /* @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(remoteKey: List<RemoteKey>)

    @Query("SELECT * FROM remote_keys WHERE movieId = :id")
    suspend fun remoteKeysMovieId(id: Int): RemoteKey?

    @Query("DELETE FROM remote_keys")
    suspend fun deleteAll()*/

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertKeys(keys: List<RemoteKey>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertKey(key: RemoteKey)

    @Query("select * from remote_keys where id =:key")
    fun getKeyByMovie(key: String): RemoteKey?

    @Query("delete from remote_keys")
    fun clearKeys()
}

