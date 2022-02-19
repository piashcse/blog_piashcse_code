package com.piashcse.experiment.mvvm_hilt.data.repository

import com.piashcse.experiment.mvvm_hilt.data.datasource.local.room.User
import com.piashcse.experiment.mvvm_hilt.data.datasource.local.room.UserDao
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RoomDBRepository @Inject constructor(private val db: UserDao) {
    suspend fun insertUser(user: User) {
        db.insertAll(user)
    }

    fun getAllUser(): Flow<List<User>> {
        return db.getAll()
    }

}