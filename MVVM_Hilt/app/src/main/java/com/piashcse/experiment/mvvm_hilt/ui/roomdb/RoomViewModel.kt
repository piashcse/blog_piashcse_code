package com.piashcse.experiment.mvvm_hilt.ui.roomdb

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.piashcse.experiment.mvvm_hilt.data.repository.RoomDBRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RoomViewModel @Inject constructor(private val dbRepository: RoomDBRepository) : ViewModel() {

    val getAllUser = dbRepository.getAllUser().asLiveData()
}