package com.piashcse.experiment.mvvm_hilt.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import com.piashcse.experiment.mvvm_hilt.data.datasource.local.DataStoreManager
import com.piashcse.experiment.mvvm_hilt.data.model.user.Address
import javax.inject.Inject

class DataStoreRepository @Inject constructor(private val dataStoreManager: DataStoreManager) {

    suspend fun saveUserAddress(userAddress: Address) {
        dataStoreManager.storeObjectAsJson(DataStoreManager.USER_ADDRESS, userAddress)
    }

    fun getUserAddress(): LiveData<Address> {
       return dataStoreManager.getObjectData<Address>(DataStoreManager.USER_ADDRESS).asLiveData()
    }
}