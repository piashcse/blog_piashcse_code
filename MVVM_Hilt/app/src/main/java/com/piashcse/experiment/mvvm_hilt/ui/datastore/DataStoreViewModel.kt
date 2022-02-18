package com.piashcse.experiment.mvvm_hilt.ui.datastore

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.piashcse.experiment.mvvm_hilt.data.datasource.local.DataStoreManager
import com.piashcse.experiment.mvvm_hilt.data.model.user.Address
import com.piashcse.experiment.mvvm_hilt.data.model.user.Geo
import com.piashcse.experiment.mvvm_hilt.data.repository.DataStoreRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DataStoreViewModel @Inject constructor(private val dataStoreRepository: DataStoreRepository) :
    ViewModel() {

    fun saveUserAddress(userAddress: Address) = viewModelScope.launch {
        dataStoreRepository.saveUserAddress(userAddress)
    }

    fun getUserAddress() = liveData {
        emitSource(dataStoreRepository.getUserAddress())
    }
}