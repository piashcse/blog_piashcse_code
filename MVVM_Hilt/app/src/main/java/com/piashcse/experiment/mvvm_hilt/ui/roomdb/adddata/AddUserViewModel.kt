package com.piashcse.experiment.mvvm_hilt.ui.roomdb.adddata

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.piashcse.experiment.mvvm_hilt.data.datasource.local.room.User
import com.piashcse.experiment.mvvm_hilt.data.repository.RoomDBRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddUserViewModel @Inject constructor(private val dbRepo: RoomDBRepository) : ViewModel() {
    fun insertUser(user: User) {
        viewModelScope.launch {
            dbRepo.insertUser(user)
        }
    }
}