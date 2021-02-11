package com.piashcse.experiment.mvvm_hilt.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.piashcse.experiment.mvvm_hilt.datasource.DataSource
import com.piashcse.experiment.mvvm_hilt.network.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val dataSource: DataSource): ViewModel(){
    fun getRepositoryList(since: String) = liveData(Dispatchers.IO){
        try {
            emit(Resource.loading())
            val response = dataSource.getRepositoryList(since)
            if (response.isSuccessful){
                emit(Resource.success(response))
            }
        }catch (e: Throwable){
            emit(Resource.error(e))
        }
    }

}