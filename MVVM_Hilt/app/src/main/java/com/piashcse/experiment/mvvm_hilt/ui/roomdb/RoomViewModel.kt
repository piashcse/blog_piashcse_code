package com.piashcse.experiment.mvvm_hilt.ui.roomdb

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.piashcse.experiment.mvvm_hilt.data.datasource.local.room.UserDao
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RoomViewModel @Inject constructor(db: UserDao) : ViewModel() {

    val getAllUser = db.getAll().asLiveData()
}