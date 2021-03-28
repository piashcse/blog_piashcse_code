package com.piashcse.experiment.mvvm_hilt.ui.viewmodel

import androidx.lifecycle.*
import com.piashcse.experiment.mvvm_hilt.datasource.DataSource
import com.piashcse.experiment.mvvm_hilt.network.Resource
import com.piashcse.experiment.mvvm_hilt.utils.jsonData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val dataSource: DataSource) : ViewModel() {

    fun getRepositoryList(since: String) = liveData(Dispatchers.IO) {
        emit(Resource.loading())
        try {
            val response = dataSource.getRepositoryList(since)
            if (response.isSuccessful) {
                emit(Resource.success(response.body()))
            } else {
                emit(Resource.error(response.errorBody()?.jsonData()))
            }

        } catch (e: Throwable) {
            emit(Resource.error(e))
        }
    }

    fun makeFlow(keyword: String) = liveData(Dispatchers.IO) {
        flowOf(keyword).debounce(300)
            .filter {
                it.trim().isEmpty().not()
            }
            .distinctUntilChanged()
            .flatMapLatest {
                dataSource.getRepositoryListFlow(it)
            }.collect {
                emit(it)
            }
    }


}